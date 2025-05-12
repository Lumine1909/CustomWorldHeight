# CustomWorldHeight

**CustomWorldHeight** is a plugin for Paper that allows you to modify the height of the Minecraft world. It provides an easy and efficient way to extend or limit the vertical dimensions of your world for creative builds, gameplay tweaks, or server optimization.

## Features

- Customize the height of your Minecraft world.
- Seamless integration with Paper.
- Easy-to-configure settings for server administrators.

## Installation

Follow these steps to install the plugin:

1. Download the latest release of **CustomWorldHeight** from the [Releases](https://github.com/Lumine1909/CustomWorldHeight/releases/latest) page.
2. Place the downloaded `.jar` file into your server's `plugins` folder.
3. Restart your Minecraft server.

## Configuration

```yaml
example-namespace: # You can set to anything you want
  world: 'world' # Set for the world named 'world'
  min-y: -64 # Min Y position
  height: 1024 # Height (max Y position = min-y + height - 1)
  logical-height: 512 # Height for Chorus Fruit teleport and etc. See wiki for details
```

## License

This project is licensed under the [MIT License](LICENSE).

## Support

If you encounter any issues or have questions, feel free to open an [issue](https://github.com/Lumine1909/CustomWorldHeight/issues).

---