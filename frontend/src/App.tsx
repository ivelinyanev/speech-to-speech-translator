import { useState, useRef } from "react";

function App() {
    const [recording, setRecording] = useState(false);
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

            // Wait for a short moment to ensure all chunks are captured
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

                    // Play the translated audio
                    const audio = new Audio(URL.createObjectURL(translatedAudioBlob));
                    await audio.play().catch((err) => {
                        console.error("Autoplay prevented:", err);
                    });
                } catch (err) {
                    console.error("Error uploading or playing audio:", err);
                }
            }, 100); // 100ms ensures MediaRecorder has finalized
        }
    };

    return (
        <div
            style={{
                display: "flex",
                height: "100vh",
                alignItems: "center",
                justifyContent: "center",
                flexDirection: "column",
            }}
        >
            <button
                onClick={handleRecordClick}
                style={{
                    width: 200,
                    height: 200,
                    borderRadius: "50%",
                    fontSize: 18,
                    backgroundColor: recording ? "#ff4d4f" : "#4caf50",
                    color: "white",
                    border: "none",
                    cursor: "pointer",
                }}
            >
                {recording ? "Stop Recording" : "Start Recording"}
            </button>
            <p>{recording ? "Recording..." : "Click the button to start"}</p>
        </div>
    );
}

export default App;