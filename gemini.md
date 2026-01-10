# MageMonkey Studio Project Overview

This document provides a high-level overview of the MageMonkey Studio projects in this repository to guide AI-based development and maintenance.

## Project Purpose

This is a multi-module Maven project containing a collection of Minecraft plugins (Spigot/Paper/Bungee), libraries, and APIs. The parent project is `magemonkey-parent`.

## Tech Stack

- **Language:** Java (version 11)
- **Build Tool:** Maven
- **Platform:** Minecraft (Spigot, Paper, BungeeCord)
- **Key Libraries:**
    - Lombok
    - Spigot/Paper/Bungee APIs
    - Various Minecraft plugin APIs (WorldGuard, Vault, PlaceholderAPI, etc.)

## Project Structure

This is a multi-module maven project. The root `pom.xml` defines all the modules and their dependencies.

### Modules

The following modules are included in this project:

-   **`codex`**: (Formerly ProMCCore) A core library/API used by other MageMonkey Studio plugins.
-   **`divinity`**: Custom items, combat, and more!
-   **`fabled`**: A powerful plugin for creating custom classes and skills, forked from SkillAPI.
-   **`fabled-parties`**: An extension for Fabled that adds party/group functionality.
-   **`fabled-enchants`**: FabledEnchants brings customization to enchantments. Change how common enchantments are, turn specific ones off, change the max levels, or create your own!
-   **`fabled-quests`**: A Quests module for Fabled.
-   **`mirage`**: A block regeneration plugin that allows users to configure blocks to have specific drops and to regenerate automatically.
-   **`sapphire`**: A vouchers plugin.
-   **`homestead`**: A housing plugin that allows users to purchase plots and manage them.
-   **`enigma`**: A random chest generation plugin.
-   **`blueprint`**: A plugin that lets Citizens NPCs build schematics. It depends on Citizens and Codex.
-   **`fusion`**: A custom crafting plugin that allows users to create custom recipes that produce unique items.
-   **`genesis-parent`**: A parent pom for the Genesis project and its modules.
    -   **`genesis`**: A versatile GUI plugin for creating in-game menus, shops, kits, command panels, and more.

## Development Workflow

### Building the Project

The entire project can be built from the root directory using Maven:

```bash
mvn clean install
```

To build a specific module, you can run the same command from within the module's directory.

### Dependencies

Most dependencies are fetched from public Maven repositories like Maven Central, Spigot's repository, and Paper's repository. Some dependencies are located in a custom repository at `https://repo.travja.dev`.

The `genesis` plugin may require manual installation of some dependencies, as noted in its `README.md`.


## Fabled Core Concepts

The core of Fabled is in the component structure for skills. Users can create skills with various Triggers that fire off effects. These effects use Conditions to check if they should run, Targeters to determine which entities should be affected, and Mechanics to determine what actually happens when the skill runs.


## Fabled Commands

Fabled's commands are managed through the `/class` command. Here are some of the most important sub-commands:

### Player Commands
-   `/class info [player]`: Displays class information.
-   `/class list [player]`: Displays account information.
-   `/class skill`: Opens the skill tree GUI.
-   `/class bind <skill>`: Binds a skill to the held item.
-   `/class cast <skill>`: Casts a skill.
-   `/class combo <skill> <combo>`: Sets a combo for a skill.
-   `/class profess <class>`: Professes to a new class.
-   `/class reset`: Resets your account data.
-   `/class ap <amount>`: Gives attribute points.
-   `/class mana <amount>`: Gives mana.
-   `/class points <amount>`: Gives skill points.
-   `/class bar`: Toggles the skill bar.
-   `/class unbind`: Unbinds all skills from the held item.
-   `/class clearbinds`: Clears all skill bindings.
-   `/class account <id>`: Switches active account.
-   `/class attribute`: Opens the attribute menu.
-   `/class options`: Displays profession options.

### Admin Commands
-   `/class forceprofess <player> <class>`: Forces a player to profess to a class.
-   `/class forcereset <player> [account]`: Forces a player's account to be reset.
-   `/class forcecast <player> <skill> [level]`: Forces a player to cast a skill.
-   `/class forceaccount <player> <account>`: Forces a player to switch accounts.
-   `/class forceattr <player> [attribute] [amount]`: Manages a player's attributes.
-   `/class forceskill <player> <up|down|reset> <skill>`: Modifies a player's skill level.
-   `/class exp <player> <add|set|remove> <amount> [group]`: Manages a player's experience.
-   `/class level <player> <add|set|remove> <amount> [group]`: Manages a player's level.
-   `/class mana <player> <amount>`: Gives a player mana.
-   `/class points <player> <amount>`: Gives a player skill points.
-   `/class reload`: Reloads the plugin.
-   `/class backup`: Backs up SQL data.

## Fabled Component System

The dynamic skill system in Fabled is built upon a modular component architecture. The `ComponentRegistry` class is the central hub for managing these components.

### Component Types

There are four types of components:

*   **Triggers**: These initiate a skill's effects. They are tied to specific Bukkit events (e.g., `PlayerInteractEvent`, `EntityDamageByEntityEvent`). Examples include `ClickRightTrigger`, `KillTrigger`, and `LandTrigger`.
*   **Targets**: These determine which entities are affected by a skill. They define the area or pattern to select targets in. Examples include `AreaTarget`, `ConeTarget`, and `SelfTarget`.
*   **Conditions**: These are checks that must pass for the subsequent mechanics to execute. They act as filters. Examples include `ChanceCondition`, `HealthCondition`, and `WorldCondition`.
*   **Mechanics**: These are the actions that happen when a skill is used. They are the core logic of a skill. Examples include `DamageMechanic`, `HealMechanic`, and `LaunchMechanic`.

### Registration

The `ComponentRegistry` uses a `static` block to register all built-in components on startup.

*   `ComponentRegistry.register(new MyComponent())` is used to add new components.
*   For Triggers, this method creates a corresponding `EventExecutor` for performance, linking the Bukkit event system to Fabled's trigger handling.
*   For other components, it stores the component's `Class` in a map, keyed by its `ComponentType` and string key. A new instance of the component is created via reflection whenever a skill needs it.

### Dynamic Editor Integration

The component system is tightly integrated with the external skill editor.

*   Components implement `CustomComponent`, which provides methods like `getKey()`, `getDisplayName()`, `getDescription()`, and `getOptions()`.
*   This information is serialized into a `tool-config.json` file by the `ComponentRegistry.save()` method.
*   The external skill editor reads this JSON file to dynamically generate the UI, showing available components and their configurable options.

### How to Register a New Component

1.  **Create the Component Class**: Your class must implement the appropriate interface (`Condition`, `Mechanic`, `Target`, or `Trigger`) and likely extend `CustomEffectComponent` or a similar base class.
2.  **Implement Core Methods**:
    *   Provide a public, no-argument constructor.
    *   `getKey()`: Return a unique string key (e.g., "my-new-mechanic").
    *   `getDisplayName()`: Return a user-friendly name (e.g., "My New Mechanic").
    *   `getType()`: Return the correct `ComponentType` enum.
    *   `getDescription()`: Provide a description for the editor.
    *   `getOptions()`: Define a list of `EditorOption`s for the configurable fields.
    *   `execute(...)` (or similar): Implement the component's logic.
3.  **Register it**: In a suitable place during plugin startup, call `ComponentRegistry.register(new MyNewComponent());`.

## Fabled Class and Attribute System

The class and attribute systems are foundational to Fabled, defining player progression and stats.

### Class System (`FabledClass`)

Classes in Fabled are templates that define a player's abilities and progression path.

*   **Configuration-Based**: Classes are not hard-coded but are defined in configuration files (likely YAML). The `FabledClass` abstract class provides the structure, and its `load()`/`save()` methods handle serialization.
*   **Class Hierarchy (Professions)**: Classes can have a `parent`. This creates a profession tree where players can "profess" into more advanced classes once they meet certain requirements (like reaching the max level of the parent class).
*   **Groups (Multi-classing)**: Each class belongs to a `group` (e.g., "class", "race"). This system allows a player to be part of multiple classes simultaneously, as long as each class is in a different group. The player gains the stats and skills from all of their professed classes.
*   **Skills and Experience**: Each class definition includes a list of skills available to that class. It also defines the allowed `ExpSource` types, meaning a class can be configured to gain experience only from specific actions (e.g., killing mobs, crafting, or commands).

### Attribute System (`AttributeManager`)

The `AttributeManager` governs all stats available in the game, loading them from `attributes.yml`.

*   **Attribute Definition**: This manager defines what attributes exist (e.g., Vitality, Strength, Intellect). Each is an instance of `FabledAttribute`, which encapsulates its name, icon, and how it affects underlying stats.
*   **Stat Mapping**: Attributes are mapped to a large number of built-in stats, including both vanilla Minecraft attributes (`generic.max_health`, `generic.attack_damage`) and custom stats created by Fabled (`skill-damage`, `mana-regen`, `cooldown`).

### How Classes and Attributes Work Together

1.  **Distribution**: A class's configuration file specifies how attributes are granted to a player. For each attribute (like `health`), it defines a base value and a value per level (e.g., `health-base: 20`, `health-scale: 2`).
2.  **Calculation**: `PlayerData` is the central object for a player's Fabled data. It calculates the player's final stats by summing up the attributes gained from all of their professed classes at their current levels.
3.  **Attribute Points**: As players level up, they earn attribute points. The `/class attribute` command opens a menu where players can invest these points into their attributes, further customizing their stats. `CmdForceAttr` and `PlayerData` have methods for managing these invested points.

## Fabled Data Storage

Fabled supports multiple data storage types for player data.

*   **MySQL Database**: The primary supported database storage is MySQL. Connection details are configured in the main `config.yml`. The `SQLManager` class handles the connection and the `FabledPlayersSQL` class acts as a DAO to manage player data persistence. The system includes schema migration capabilities.
*   **Flat-File YAML**: By default, or if SQL is disabled, player data is saved in individual YAML files.
*   **Backup Command**: The `/class backup` command provides a way to back up player data from the SQL database into local YAML files, which can be useful for server maintenance or migration.

## Fabled GUI System

Fabled's GUIs, such as the skill tree, are primarily built using a map-based system provided by the `codex` core library.

*   **Codex `MapMenuManager`**: This is the central class for the GUI system. It allows for the creation of complex, multi-page menus that are rendered on in-game maps (`Material.MAP`).
*   **Registration**: Menus are registered with a unique key (e.g., `SKILL_TREE`). The manager handles creating or loading the underlying `MapView` and saves its ID to `maps.yml` for persistence.
*   **Interaction**: A `MapListener` in `codex` listens for player interactions while they are holding a menu map, allowing for navigation and click handling within the rendered GUI. Fabled uses this for its skill tree (`/class skillmap`), profession menu, and attribute menu.

## Codex Core Library

The `codex` module is the core engine and shared library that powers all other MageMonkey Studio plugins in this repository. It provides a robust framework of reusable systems and utilities.

### Core Patterns & Features

*   **Engine Singleton**: The `CodexEngine` class is a singleton (`CodexEngine.get()`) that acts as the central hub for the library's features. It's a `JavaPlugin` itself, which all other plugins depend on.
*   **`CodexPlugin` Base Class**: Instead of extending `JavaPlugin`, other plugins in the ecosystem extend the abstract `CodexPlugin` class. This provides a clear inheritance structure and automatically hooks them into the `CodexEngine`.
*   **Manager-Based Architecture**: Codex follows a manager/service locator pattern, providing centralized access to various systems:
    *   **`HookManager`**: Manages all soft-dependency integrations with external plugins like Vault, WorldGuard, Citizens, and MythicMobs.
    *   **`CodexItemManager`**: A system for creating and managing custom items.
    *   **`ActionsManager`**: A powerful, configurable system for creating sequences of actions that can be triggered by skills, items, or events.
    *   **`CraftManager`**: For creating custom crafting recipes.
    *   **`PacketManager`** & **`VersionManager`**: A comprehensive framework for handling version-specific NMS/OBC code, ensuring cross-version compatibility.
*   **Configuration Framework**:
    *   Provides a `Config` class wrapper around Bukkit's `FileConfiguration`.
    *   Includes a `getConfigFile()` method that allows any child plugin to get a managed config file that is automatically loaded and saved by the engine.
*   **`mccore` Package**: This is a sub-package within Codex containing many of the most commonly used shared systems:
    *   **Commands**: A command framework using `ConfigurableCommand` and `IFunction` interfaces, seen in `Fabled` and other plugins.
    *   **GUI**: A map-based GUI system (`MapMenuManager`) and likely other inventory-based GUI utilities.
    *   **Scoreboard**: A reusable scoreboard management system.
    *   **Config**: The underlying configuration parsing and management library.

## Codex NMS Framework

To support multiple Minecraft versions without cluttering the core logic, Codex uses a common abstraction pattern for handling version-specific `net.minecraft.server` (NMS) and `org.bukkit.craftbukkit` code.

### Core Components

1.  **The `NMS` Interface**:
    *   Located at `codex/codex-api/src/main/java/studio/magemonkey/codex/compat/NMS.java`.
    *   This interface defines the "contract" for all NMS-dependent functionality. It includes methods for packet handling, accessing item attributes, creating client-side effects, and other tasks that change between Minecraft versions.
    *   Any new version-specific implementation must implement this interface.

2.  **The `VersionManager` Factory**:
    *   Located at `codex/codex-api/src/main/java/studio/magemonkey/codex/compat/VersionManager.java`.
    *   This class acts as a factory. On server startup, its `setup()` method detects the current server version (e.g., "1.21.10").
    *   It uses a `switch` statement to map the server version to a specific package name (e.g., "v1_21_10").
    *   It then uses reflection to dynamically load the `NMSImpl` class from that package (e.g., `studio.magemonkey.codex.nms.v1_21_10.NMSImpl`).
    *   The single instance of the correct NMS implementation is stored statically and accessed throughout the project via `VersionManager.getNms()`.

3.  **Concrete Implementations**:
    *   Each supported Minecraft version has its own module in the `codex/codex-nms/` directory (e.g., `codex-nms-v1_21_10`).
    *   Inside each module is the concrete implementation class (e.g., `NMSImpl.java`) that implements the `NMS` interface and contains the actual, version-specific NMS and CraftBukkit code.

### How to Add Support for a New Minecraft Version

1.  **Create New Module**: In the `codex/codex-nms/` directory, create a new module for the new version (e.g., `codex-nms-v1_22_1`). You can copy an existing module as a template.
2.  **Update `pom.xml`**: In the new module's `pom.xml`, update the Spigot/Paper dependency to the new Minecraft version.
3.  **Copy and Update Implementation**: Copy the `NMSImpl.java`, `ArmorUtilImpl.java`, and `CompatImpl.java` from a previous version's module into your new module's source folder.
4.  **Fix Code**: Update the imports and method bodies in the new implementation files. This is the primary task. You will need to decompile the new server JAR or use mapping tools to find the updated NMS/CraftBukkit class names, field names, and method signatures, then resolve all compilation errors.
5.  **Update `VersionManager`**: In `VersionManager.java`, add a new `case` to the `switch` statement to map the new version string to your new package name.
6.  **Update Parent POM**: Add the new NMS module to the `<modules>` section of the main `codex/codex-nms/pom.xml`.


## Divinity Plugin

`Divinity` (formerly ProRPGItems) is a major content plugin that provides a powerful framework for creating advanced, RPG-style custom items and managing related game mechanics. It is built on top of `Codex` and shares many of its core architectural patterns.

### Core Features

*   **Manager-Based Architecture**: Like `Codex`, Divinity uses a suite of managers for its core systems:
    *   `DamageManager`: A critical component that manages custom damage calculations, damage types (e.g., `SKILL_DAMAGE`, `PHYSICAL_DAMAGE`), and resistance handling. It registers itself with Codex's `DamageRegistry`.
    *   `InteractionManager`: Handles player interactions with items.
    *   `ProfileManager`: Manages "profiles", a feature for creating distinct character setups.
    *   `ModuleCache`: Manages the various item modules.
    *   `WorthManager`: Calculates the economic value of items.
*   **Modular Custom Item System**: Divinity's most significant feature is its modular system for custom items. Different capabilities are handled by separate "item modules", allowing for a wide variety of complex items. Key built-in modules include:
    *   `LeveledItem`: Items that can gain their own experience and level up.
    *   `SocketItem`: Items with empty sockets that can be filled with other items (like gems) to grant bonuses.
    *   `LimitedItem`: Items with a limited number of uses before they break or become inert.
    *   `UsableItem`: Items with custom right-click or other triggerable actions, which are integrated with Codex's `ActionsManager`.
*   **Codex Integration**:
    *   `Divinity` extends `CodexDataPlugin`, using the underlying data management systems from Codex for both SQL and YAML storage.
    *   It registers a `DivinityProvider` with the `CodexItemManager`, making its custom items available to the entire ecosystem.
    *   It adds its own custom conditions and mechanics to the `ActionsManager`, such as `ActionDamage` and `CEntityLevel`.

### Divinity: Item Sockets & Gems

One of Divinity's most powerful features is its generic and extensible item socketing system. The primary example of this is the "Gems" module.

#### The Core Concepts

1.  **Host Items (Socketable Items)**:
    *   Any item can be made socketable by adding `SocketAttribute`s to its configuration (e.g., in a custom `items.yml`).
    *   These attributes define the **type** and **number** of sockets the item has. For example, an item could be given two "Power" sockets and one "Utility" socket.
    *   Example configuration for an item: `attributes.socket-power: 2`

2.  **Socketable Items (Gems, Essences, etc.)**:
    *   These are separate custom items that are designed to be placed inside the sockets of a host item. The primary example is a "Gem", managed by the `GemManager`.
    *   The Gem's configuration file specifies what **type of socket** it can fit into. This is a hard requirement (e.g., `target-requirements.socket: power`).
    *   The Gem's configuration also defines the stat bonuses (`BonusMap`) and/or skills/abilities (`AbilityGenerator`) that it grants. These bonuses can be defined per-level, allowing gems to become more powerful as they level up.

3.  **The Socketing Process**:
    *   The system is primarily driven by a **drag-and-drop** inventory interaction, handled by the generic `ModuleSocket` class.
    *   A player drags a Gem onto a valid Host Item.
    *   This action opens a confirmation GUI (`UserGUI`).
    *   Upon confirmation, the `ModuleSocket.insertSocket` method is called. It finds a matching empty socket on the host item and writes the Gem's ID and level into the host item's NBT data.
    *   The more specific `GemManager.insertSocket` override is then called, which applies the Gem's abilities to the host item.

4.  **Stat and Ability Application**:
    *   The stats of the host item are dynamically recalculated to include the bonuses from all socketed gems.
    *   Abilities granted by gems are added to the host item's NBT data and become available for use. When the gem is extracted, the `extractSocket` method ensures these abilities are removed.

5.  **Extensibility**:
    *   The entire system is built around the generic `ModuleSocket<I extends SocketItem>` class. This means developers can easily create new types of socketable items (e.g., "Runes", "Essences", "Jewels") by creating a new Manager class that extends `ModuleSocket` and a new Item class that extends `SocketItem`, without having to reinvent the core socketing logic.

### Divinity: Procedural Item Generation

The core of Divinity's loot system is a powerful procedural item generator, managed by the `ItemGeneratorManager`. This system allows for the creation of highly randomized, ARPG-style loot based on configurable templates.

#### Core Concepts

1.  **Generator Templates (`GeneratorItem`)**:
    *   The foundation of the system is a set of YAML templates located in the `/plugins/Divinity/modules/item_generator/items/` directory. Each file (e.g., `common.yml`, `rare.yml`) represents a "tier" of items.
    *   These templates define the rules and probabilities for every aspect of the items to be generated.

2.  **The Generation Process**:
    *   When an item is generated from a template (e.g., "common"), the `ItemGeneratorManager` executes a complex, multi-step `build` process.
    *   **Base Item Selection**: A base `Material` is randomly selected from a configurable list of allowed or disallowed materials.
    *   **Name Generation**: The item's name is dynamically constructed using placeholders for prefixes and suffixes (e.g., `%prefix_tier%`, `%suffix_material%`). These affixes are loaded from the `ResourceManager`, which categorizes them by type (tier, material, item type).
    *   **Randomized Properties**: The system then rolls for a wide array of properties. Each property in the template has a `chance` to be included, a `min` and `max` number of rolls, and a `scale-by-level` factor. This includes:
        *   **Stats**: Standard item stats (`item-stats`), custom damage types (`damage-types`), custom defense types (`defense-types`), and Fabled attributes (`fabled-attributes`).
        *   **Enchantments**: A random number of enchantments with randomized levels.
        *   **Sockets**: The generator rolls to determine how many `GEM`, `ESSENCE`, or `RUNE` sockets the item should have, and of what rarity.
        *   **Skills/Abilities**: The item can be generated with random skills attached to it.
    *   **Final Assembly**: The system assembles all the generated properties into a final `ItemStack`, calculating the final stat values and constructing the item's lore with all the relevant information.

3.  **Extensibility (`IAttributeGenerator`)**:
    *   The process of generating a specific category of stats (like `item-stats` or `sockets`) is handled by classes that implement the `IAttributeGenerator` interface.
    *   This makes the system highly extensible. To add a new type of randomly generated property to items, one only needs to create a new class implementing this interface and add it to the `attributeGenerators` set within the `GeneratorItem` class.

This procedural generation system is what gives Divinity its powerful and replayable loot experience, allowing for a nearly infinite variety of unique items.

## How to Use This File in Future Sessions

At the beginning of your next `gemini` session in this directory, you can provide this file as context by using a command like:

```
gemini "Using the context from this file, <your question here>" gemini.md
```
