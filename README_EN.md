# Simple Aether Cloud Mod

English | [简体中文](README.md)

A simple Minecraft mod that adds various cloud blocks. The original idea comes from The Aether mod, with additional features added.

- Added yellow cloud, blue cloud, horizontal blue cloud, red cloud, and green cloud blocks
- Cloud blocks can be placed and broken like normal blocks
- Cloud blocks have special collision boxes that allow players to pass through
- Red cloud deals damage to entities that touch it
- Green cloud accelerates entity growth and promotes breeding

## Crafting Recipes

### Yellow Cloud

```
Sugar + Yellow Dye = Yellow Cloud
```

### Blue Cloud

```
Sugar + Blue Dye = Blue Cloud
```

### Horizontal Blue Cloud

```
Blue Cloud = Horizontal Blue Cloud
Horizontal Blue Cloud = Blue Cloud
```

### Random Blue Cloud

```
Any Blue Cloud Block = Random Blue Cloud
```

### Red Cloud

```
Sugar + Red Dye = Red Cloud
```

### Green Cloud

```
Sugar + Green Dye = Green Cloud
```

## Features

### Common Features

- Cloud blocks can be placed and broken like normal blocks
- Cloud blocks have special collision boxes that allow players to pass through
- Cloud blocks display as 3D models in inventory

### Yellow Cloud

- Prevents fall damage
- Cannot pass through block bottoms

### Blue Cloud

- Prevents fall damage
- Launches entities in the specified direction
- Sneaking prevents launch and slowly sinks

### Horizontal Blue Cloud

- Same effects as Blue Cloud, but launch direction is fixed to horizontal

### Random Blue Cloud

- Prevents fall damage
- Randomly launches entities in one of six directions
- Hold Shift to pass through normally

### Red Cloud

- Prevents fall damage
- Deals damage to entities
- Sneaking makes you immune to damage

### Green Cloud

- Prevents fall damage
- Accelerates entity growth
- Speeds up breeding cooldown
- Attracts nearby animals
- Animals in the cloud will periodically attempt to breed

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