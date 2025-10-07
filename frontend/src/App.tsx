import { useState, useRef } from "react";
import "./App.css";

function App() {
    const [recording, setRecording] = useState(false);
    const [loading, setLoading] = useState(false);
    const mediaRecorderRef = useRef<MediaRecorder | null>(null);
    const audioChunksRef = useRef<Blob[]>([]);

    const handleRecordClick = async () => {
        if (!recording) {
            // Start recording
            try {
                const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
                const mediaRecorder = new MediaRecorder(stream);
                mediaRecorderRef.current = mediaRecorder;
                audioChunksRef.current = [];

                mediaRecorder.ondataavailable = (event) => {
                    if (event.data.size > 0) {
                        audioChunksRef.current.push(event.data);
                    }
                };

                mediaRecorder.start();
                setRecording(true);
            } catch (err) {
                console.error("Microphone access denied or not available:", err);
            }
        } else {
            // Stop recording
            mediaRecorderRef.current?.stop();
            setRecording(false);
            setLoading(true);

            setTimeout(async () => {
                const audioBlob = new Blob(audioChunksRef.current, { type: "audio/mp3" });
                const formData = new FormData();
                formData.append("file", audioBlob, "recording.mp3");

                try {
                    const response = await fetch("http://localhost:8080/api/translation/upload", {
                        method: "POST",
                        body: formData,
                    });

                    if (!response.ok) throw new Error("Failed to send audio");

                    const arrayBuffer = await response.arrayBuffer();
                    const translatedAudioBlob = new Blob([arrayBuffer], { type: "audio/mp3" });

                    const audio = new Audio(URL.createObjectURL(translatedAudioBlob));
                    await audio.play().catch((err) => {
                        console.error("Autoplay prevented:", err);
                    });
                } catch (err) {
                    console.error("Error uploading or playing audio:", err);
                } finally {
                    setLoading(false);
                }
            }, 100);
        }
    };

    return (
        <div className="app-container">
            <div className="button-wrapper">
                <button
                    onClick={handleRecordClick}
                    className={`record-btn ${recording ? "recording" : ""}`}
                    disabled={loading}
                >
                    {loading ? (
                        <div className="spinner"></div>
                    ) : recording ? (
                        "Stop"
                    ) : (
                        "Record"
                    )}
                </button>
                <p className="status-text">
                    {loading
                        ? "Translating..."
                        : recording
                            ? "Recording..."
                            : "Click to start recording"}
                </p>
            </div>
        </div>
    );
}

export default App;