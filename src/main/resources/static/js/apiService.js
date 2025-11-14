const API_BASE = ""; // samme origin (localhost:8080)

export default async function askAI(prompt) {
    const response = await fetch(API_BASE + "/api/movies/popular-list", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ prompt: prompt }),
    });

    if (!response.ok) {
        throw new Error("Server error: " + response.status);
    }

    // backend: { "response": "..." }
    return await response.json();
}
