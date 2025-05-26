# Simple Aether Cloud Mod

English | [简体中文](README.md)

A simple Minecraft mod that adds various cloud blocks. The original idea comes from The Aether mod, with additional features added.

- Added yellow cloud, blue cloud, red cloud, green cloud, and black cloud blocks
- Cloud blocks can be placed and broken like normal blocks
- Cloud blocks prevent fall damage

## Features

### Yellow Cloud

- Cannot pass through block bottoms

### Blue Cloud

- Launches entities in the specified direction
- Sneaking prevents launch and slowly sinks
- Other variant features can be found in-game

### Red Cloud

- Deals damage to entities
- Sneaking makes you immune to damage

### Green Cloud

- Accelerates entity growth
- Speeds up breeding cooldown
- Attracts nearby animals
- Animals in the cloud will periodically attempt to breed

### Black Cloud

- Blocks or allows entities to pass through
- Recognized as solid blocks by mobs
- Can spawn mobs on top when in blocking state
- Other variant features can be found in-game

## Requirements

- Minecraft 1.21 ~ 1.21.1
- NeoForge 21.0.167 or higher

## Building

1. Clone the repository

```bash
git clone https://github.com/BAKAOLC/Simple-Aether-Cloud-Mod.git
```

2. Enter the project directory

```bash
cd Simple-Aether-Cloud-Mod
```

3. Build the project

```bash
./gradlew build
```

After building, you can find the generated mod file in the `build/libs` directory.

## License

This project is licensed under LGPL-3.0. See the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Inspired by [The Aether Mod](https://github.com/The-Aether-Team/The-Aether), but completely reimplemented