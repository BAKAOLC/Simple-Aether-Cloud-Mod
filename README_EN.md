# Simple Aether Cloud Mod

A simple Minecraft mod that adds various cloud blocks. The original idea comes from The Aether mod, with added features.

English | [简体中文](README.md)

## Features

- Adds various cloud blocks including yellow, blue, horizontal blue, and red clouds
- Cloud blocks can be placed and broken like normal blocks
- Cloud blocks have special collision boxes that allow entities to pass through
- Red cloud will damage entities that come in contact with it

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

The Horizontal Blue Cloud will push entities in the direction the block is facing. You can convert between Blue Cloud and Horizontal Blue Cloud using shapeless crafting.

### Red Cloud
```
Sugar + Red Dye = Red Cloud
```

## Features

- Cloud blocks can be placed and broken like normal blocks
- Cloud blocks have special collision boxes that allow entities to pass through
- Red cloud will damage entities that come in contact with it
- Cloud blocks display as 3D models in inventory

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

This project is licensed under the LGPL-3.0 License. See the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Some code references from [The Aether Mod](https://github.com/The-Aether-Team/The-Aether) 