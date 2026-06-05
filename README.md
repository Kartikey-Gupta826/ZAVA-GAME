# AI RPG Adventure (Gemini-Powered)

A text-based RPG adventure game built in Java and powered by Google's Gemini API.

Travel across a cursed land, encounter AI-generated villains, engage in dynamic roleplay conversations, survive combat encounters, and increase your score as you progress toward victory.

---

## Features

### AI Story Narration

* Game introduction generated using Gemini.
* Dark fantasy narration style.
* Dynamic scene descriptions.

### AI Villains

* Every encounter generates a unique villain.
* Villains introduce themselves with original names and personalities.
* Villains remember the current conversation during the encounter.
* Real-time roleplay powered by Gemini.

### Exploration System

* Move around a 6×6 world grid.
* Travel using:

  * Up
  * Down
  * Left
  * Right
* Track your current coordinates.

### Combat System

* Attack or defend during villain encounters.
* Damage scales with villain level.
* Health points (HP) decrease based on battle outcomes.
* Score increases when defeating enemies.

### Lottery Shrine

* Special healing location at coordinate (1,1).
* Roll a dice and test your luck.
* Restore or increase HP based on the result.

### Win Condition

* Reach a score of 60 or higher to win the game.

### Game Over Condition

* HP reaches zero.

---

## Technologies Used

* Java
* Gemini API
* Google GenAI SDK
* Dotenv Java

---

## Project Structure

```text
src/
│
├── Main.java
│   ├── Movement System
│   ├── Combat System
│   ├── Lottery Shrine
│   ├── Score Tracking
│   └── Game Loop
│
└── GeminiChat.java
    ├── AI Narrator
    ├── Villain Generator
    ├── Villain Conversation Memory
    └── Gemini API Integration
```

---

## Setup

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/ai-rpg-adventure.git
cd ai-rpg-adventure
```

### 2. Add Environment Variables

Create a `.env` file:

```env
GOOGLE_API_KEY=YOUR_GEMINI_API_KEY
```

### 3. Install Dependencies

Required libraries:

```xml
Google GenAI SDK
dotenv-java
```
### 4. Run

```bash
java Main
```

---

## Gameplay Example

```text
Welcome to the game!

Black clouds crawl across the horizon as a lone warrior
steps into cursed lands. Ancient foes stir in the dark,
hungry for blood and glory :)

Enter your name:
Arjun

Your weapon is: Sword
Your armor is: Shield

Current Position: (0,0)

Move:
right

A level 7 villain has appeared.

I am Varkhul the Ash-Crowned, breaker of kingdoms.
Your journey ends beneath my shadow, hero.

Arjun:
I fear no darkness.

Fear is the language of mortals, and I have long forgotten
how to speak it.

Do you want to attack or defend?
```

---

## Game Mechanics

### Starting Stats

```text
HP: 100
Weapon: Sword
Armor: Shield
Score: 0
```

### Villain Levels

```text
Level Range: 1 - 10
```

### Attack

```text
Damage Taken = 10 × Villain Level
Score Gain = 5 × Villain Level
```

### Defend

```text
Damage Taken = 1 × Villain Level
```

### Victory

```text
Score >= 60
```

---

## Future Improvements

* Inventory system
* Multiple weapons
* Armor upgrades
* Save and load game
* Quest system
* Boss battles
* NPC interactions
* Character classes
* Experience and leveling
* Procedurally generated world
* Long-term AI memory
* Multiplayer support

---

## Author

Built as a Java console RPG experiment using Google's Gemini API to create dynamic narration and villain roleplay.

---

## License

MIT License
