import { handleChatSubmit, handleKeyDown } from "./formSubmit.js";

window.addEventListener("DOMContentLoaded", initApp);

function initApp() {
    console.log("Movie Chatbot app initialized");
    setupEventListeners();
}

function setupEventListeners() {
    const chatForm = document.querySelector("#chat-form");
    const textarea = chatForm.querySelector("textarea");

    chatForm.addEventListener("submit", handleChatSubmit);
    textarea.addEventListener("keydown", handleKeyDown);
}
