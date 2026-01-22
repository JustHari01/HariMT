# HariMT

<div align="center">

![HariMT Logo](https://via.placeholder.com/150?text=HariMT)

**High-Performance Asynchronous Entity Ticking for Minecraft**

![License](https://img.shields.io/badge/License-GPL--3.0-blue.svg)
![Minecraft Version](https://img.shields.io/badge/Minecraft-1.20.1-green.svg)
![Forge](https://img.shields.io/badge/Support-Forge-orange.svg)
![Fabric](https://img.shields.io/badge/Support-Fabric-cream.svg)

</div>

---

## 🚀 Overview

**HariMT** is a highly optimized fork of the [Async](https://github.com/Bliss-tbh/Async-1.20.1) mod, designed to significantly improve server performance by offloading entity ticking to parallel threads.

This project is based on the **Forge base** of the original Async mod by [Bliss-tbh](https://github.com/Bliss-tbh/Async-1.20.1) and Axalotl, ensuring robust compatibility and stability while introducing further optimizations for modern modpacks.

## ✨ Key Features

- **Asynchronous Entity Ticking**: Moves entity calculations off the main thread to utilize multi-core CPUs effectively.
- **Performance Optimized**: Reduces MSPT (Milliseconds Per Tick) for smoother gameplay and higher TPS.
- **Configurable Parallelism**: Adjust thread pool size and behavior via `harimt.toml`.
- **Mod Compatibility**: built-in compatibility for popular optimization mods like Lithium, Radium, and VMP.

## 📥 Installation

1.  Download the latest release from [Releases](https://github.com/JustHari01/HariMT/releases).
2.  Place the `.jar` file into your Minecraft `mods` folder.
3.  Launch the game! The config file `harimt.toml` will be generated in your config directory.

## 🛠️ Configuration

You can customize **HariMT** by editing `config/harimt.toml`:

```toml
[general]
# Maximum number of threads for parallel processing (-1 = Auto)
paraMax = -1

# Enable async processing for entity spawning (Experimental)
enableAsyncSpawn = true
```

## 📜 Credits & License

**HariMT** is licensed under the **GNU General Public License v3.0 (GPL-3.0)**.

*   **Original Author**: [Axalotl](https://github.com/Axalotl) & [Bliss-tbh](https://github.com/Bliss-tbh)
*   **Original Repository**: [Async-1.20.1](https://github.com/Bliss-tbh/Async-1.20.1)
*   **Maintainer**: HariMT

We encourage users to check out the original project and support the original creators.

---
<div align="center">
Made with ❤️ by HariMT
</div>
