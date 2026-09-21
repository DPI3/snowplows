# Snowplow Simulator 🚜❄️

A 2D top-down snowplow/city-management simulation game written in **Java (Swing)**, built around a clean **MVC architecture** and several classic **object-oriented design patterns** (Strategy, Observer, Role-based behaviour). Two players cooperate/compete to keep a road network clear of snow and ice while running bus routes, earning money, and upgrading their equipment in an in-game store.

## Documentation

- 📄 [Full weekly team documentation](https://github.com/czdotL/Snowplows/blob/main/o%CC%88sszesitett_merged.pdf) — detailed, week-by-week write-up of the team's design decisions, sprint progress, and iteration history across the skeleton → prototype → graphical stages.
- 📄 [Project documentation (summary)](https://github.com/czdotL/Snowplows/blob/main/Snowplow_Documentation.pdf) — condensed overview of the architecture, design patterns, and features.

## Gameplay

- Control a **snowplow** or a **bus** on a procedurally laid out grid-based road network (roads, bridges, tunnels, intersections).
- Dynamic **weather system**: snow accumulates over time, turns into ice, and spreads faster on bridges while tunnels stay clear.
- Manage consumables — **salt**, **gravel**, and **biokerosene** fuel — to keep your vehicle running and your lanes safe.
- Switch between **Cleaner** and **Bus Driver** roles to score points in different ways.
- Visit the **Store** to buy new plow heads and upgrades using money earned during play.
- Avoid collisions with AI-driven traffic cars; accidents block lanes and disable buses temporarily.
- Play solo or with a second player (1–5 players supported by the underlying model).

### Controls

| Key | Action |
|---|---|
| `W A S D` / Arrow keys | Move |
| `Space` | Stop |
| `B` | Open store |
| `C` | Change plow head |
| `P` | Pause |
| `Enter` | Confirm |
| `Esc` | Open menu |
| `R` | Restart |

## Architecture

The project follows a **Model–View–Controller** pattern, split into three packages:

```
src/        Domain model — vehicles, roads, weather, roles, store, game state
controller/ Game loop, input handling, screen navigation, asset/sound management
view/       Swing UI panels and screens (menu, game board, store, settings)
```

Key design patterns used:

- **Strategy** — `Head` is an abstract cleaning strategy with concrete implementations (`ThrowerHead`, `SweeperHead`, `IcebreakerHead`, `SaltSpreaderHead`, `GravelSpreaderHead`, `DragonHead`) that a `Snowplow` can swap at runtime.
- **Role** — `Role` (`CleanerRole`, `BusdriverRole`) decouples *what a player can do* from the `Player` itself; a player can hold and switch between roles.
- **Observer** — `ModelObservable` / `ModelObserver` let views react to model state changes without tight coupling.
- **Interface segregation** — `Buyable` is implemented by anything purchasable in the `Store` (heads, upgrades, vehicles).

### Core model highlights

- `RoadNetwork`, `Road` (`NormalRoad`, `Bridge`, `Tunnel`), `Lane`, `Intersection`, `Node`, `Route` — graph-based road/lane simulation with pathfinding support.
- `LaneState` and weather conditions (`Clear`, `ThinSnow`, `DeepSnow`, `IceSheet`, `BrokenIce`, `Gravel`) model how each lane degrades and recovers over time.
- `Vehicle` (`Snowplow`, `Bus`, `Car`) — shared movement/tick logic with per-type behaviour.
- `Game` — central simulation clock (`tick()`), round management, collision detection, and win/lose state.


## Requirements

- JDK 8 or newer

## Running the game

```bash
# Compile
javac -d bin $(find . -name "*.java")

# Run
java -cp bin Main
```

Or open the project in any Java IDE (IntelliJ IDEA, Eclipse, VS Code) and run `Main.java`.

## Project structure

```
graphical/
├── Main.java              # Application entry point
├── ModelObservable.java   # Observer pattern base class
├── ModelObserver.java     # Observer pattern interface
├── controller/            # Game loop, input, screens, audio, assets
├── src/                   # Domain model (vehicles, roads, weather, roles, store)
├── view/                  # Swing UI (screens, panels, HUD)
└── sounds/, *.png, *.ttf  # Game assets
```

## License

This project was created for educational purposes.
