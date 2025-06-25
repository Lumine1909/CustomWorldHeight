# CustomWorldHeight

**CustomWorldHeight** is a plugin for Paper that allows you to modify the height of the Minecraft world. It provides an easy and efficient way to extend or limit the vertical dimensions of your world for creative builds, gameplay tweaks, or server optimization.

# Important

> [!CAUTION]
> 
> This plugin is similar as the vanilla datapack, so there will be some issues as the datapack one.
> 
> One you set the height of a certain world, it will be VERY DANGEROUS to shirk the world height, all the data will lose.
> 
> Expand the height is relatively unharmful, but if you decrease the min-Y, the generated chunk will have difference with the new.
> 
> Only increase the height will only cause re-calculate chunk's height map, all the data will be properly preserved.


## Features

- Customize the height of your Minecraft world.
- Seamless integration with Paper.
- Easy-to-configure settings for server administrators.

## Installation

Follow these steps to install the plugin:

1. Download the latest release of **CustomWorldHeight** from the [Releases](https://github.com/Lumine1909/CustomWorldHeight/releases/latest) page.
2. Place the downloaded `.jar` file into your server's `plugins` folder, start the server.
3. After plugin generated the config template it will automatically disable, please finish the config.
4. Restart your Minecraft server, and the configured world's height will be modified.

## Configuration

```yaml
# You can set to anything you want, will be used to generate ResourceLocation
example-namespace:

  # Set the name for certain world
  world: 'example-world-name'

  # Min Y position
  min-y: -64

  # Height (max Y position = min-y + height - 1)
  height: 1024

  # Height for Chorus Fruit teleport and etc. See wiki for details
  logical-height: 512

  # Height for the cloud (empty -> do not have cloud, default -> use default height)
  # This feature is only for 1.21.6+
  cloud-height: 'default'

  # This will let the plugin process registration data when world load
  dimension-type: 'custom'

# You can set to anything you want, will be used to generate ResourceLocation
example-regex:

  # This plugin also support Regex matching
  regex: '^test_\w+'

  # Min Y position
  min-y: -64

  # Height (max Y position = min-y + height - 1)
  height: 1024

  # Height for Chorus Fruit teleport and etc. See wiki for details
  logical-height: 512

  # Height for the cloud (empty -> do not have cloud, default -> use default height)
  # This feature is only for 1.21.6+
  cloud-height: 256

  # For 'overworld', 'the_nether', 'the_end', 'overworld_cave', this will use built in data to generate before world is initializing
  dimension-type: 'overworld'
```

## License

This project is licensed under the [GPL 3.0 License](LICENSE).

## Support

If you encounter any issues or have questions, feel free to open an [issue](https://github.com/Lumine1909/CustomWorldHeight/issues).

---