# Overworld Coordinates

Ever had to manually calculate your portal positions for them to line up? Needed a better solution?\
Not anymore!

## What this mod does

- Adds a new line for horizontal coordinates in the scale of the overworld.
- While you are standing **in the overworld**, it additionally shows the matching nether coordinates, so you know where to aim your portal.
- The overworld line is shown in every dimension. The only dimension in vanilla this affects is the nether (with a scale of 8), but it will work with any custom dimension utilising coordinate scales!

![demonstration](https://github.com/Benonardo/Overworld-Coordinates/raw/main/demonstration.png)

## Supported versions

Fabric only. One jar is published per Minecraft version:

| Minecraft | Java  | Source set |
|-----------|-------|------------|
| 1.19.4    | 17    | `legacy`   |
| 1.20.1    | 17    | `legacy`   |
| 1.21.1    | 21    | `legacy`   |
| 1.21.10   | 21    | `modern1_21_10` |
| 1.21.11   | 21    | `modern`   |
| 26.3      | 25    | `modern`   |

## Dependencies

None! It works out of the box.  
[BetterF3](https://modrinth.com/mod/betterf3) is recommended for a better experience - when it is
installed the extra lines are rendered as part of BetterF3's coordinates module instead of the
vanilla F3 screen.

Fabric loader **0.15.11 or newer** is required, except for 26.3 which needs **0.16.5**. The mod
relies on MixinExtras (which loader embeds) and on the mixin compatibility level declared in the
mod's mixin config - `JAVA_25` is only known to the Mixin shipped with loader 0.16.5 and newer.

## Building

The repository is a multi-project Gradle build. Every Minecraft version lives in its own
subproject below `versions/`, and all of them share the sources in `src/main/java`.

```sh
# build every supported Minecraft version
./gradlew buildAll

# build a single Minecraft version
./gradlew :1.21.1:build
```

The resulting jars are placed in `versions/<mc version>/build/libs/`.

Two extra source sets exist next to the shared one, because vanilla reshuffled the internals of the
F3 screen in 1.21.10:

- `src/legacy/java` - patches `DebugScreenOverlay#getGameInformation` (1.19.4 - 1.21.1)
- `src/modern1_21_10/java` - patches `DebugEntryPosition#display` on 1.21.10
- `src/modern/java` - patches `DebugEntryPosition#display` on 1.21.11 and newer, where
  `ResourceLocation` has been renamed to `Identifier`
