// Counter state
let counter: number = 0;

// Type-safe DOM element selection
const counterDisplay = document.getElementById('counter') as HTMLSpanElement;
const incrementBtn = document.getElementById('increment-btn') as HTMLButtonElement;

// Error handling for DOM elements
if (!counterDisplay || !incrementBtn) {
    throw new Error('Required DOM elements not found');
}

// Function to update the counter display
function updateCounterDisplay(): void {
    counterDisplay.textContent = counter.toString();
}

// Function to increment counter
function incrementCounter(): void {
    counter++;
    updateCounterDisplay();
}

// Event listener for button click
incrementBtn.addEventListener('click', incrementCounter);

// Initialize display
updateCounterDisplay();