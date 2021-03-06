# EnderPlayers Minecraft Plugin

This is a plugin inspired by [Ranboo's](https://twitter.com/Ranboosaysstuff) character from the DreamSMP. He has the
unique ability to pick up block's similar to an Enderman. Download will likely be moved to the [SpigotMC website](https://spigotmc.org)
once the plugin is completely finished. This is my first somewhat legitimate plugin and github repository 
so all help/criticism is welcome and appreciated!

### Download the Plugin

[Click here](https://github.com/dirkwind/EnderPlayers/raw/main/PluginJar/EnderPlayers.jar) to download the latest version.

## Features

### Commands:

* **enderplayer**
    * **Function:** gives the specified target(s)'s every power in this plugin (or, rather, the ones specified in the config file)
    * **Description:** the enderplayers power is a culmination of every other power and is the closest a player can be to being an enderman
    * **Syntax:** /enderplayer \<targets> {true|false}
* **enderhands**
    * **Function:** sets the value of the specified target(s)'s enderhands value
    * **Description:** the enderhands power causes all block mined without an item the player's main hand will drop as is 
      (e.g. grass block will drop a grass block, stone will drop stone)
    * **Syntax:** /enderhands \<targets> {true|false|toggle}
* **enderteleport**
    * **Function:** sets the value of the specified target(s)'s enderteleport value
    * **Description:** the enderteleport power allows a player holding a ***TP Stick*** in their main hand to teleport wherever
      they look by pressing their interact button.
    * **Syntax:** /enderteleport \<targets> {true|false|toggle}
    * **Aliases:** /endertp
* **endereyes**
    * **Function:** sets the value of the specified target(s)'s endereyes value
    * **Description:** the endereyes power allows a player to safely look at the eyes of endermen
    * **Syntax:** /endereyes \<targets> {true|false|toggle}
* **enderwater**
    * **Fucntion:** sets the value of the specified target(s)'s enderwater value
    * **Description:** the enderwater power (though not really a power) causes players to take damage when in contact with water
    * **Syntax:** /enderwater <targets> {true|false|toggle}
* **tpstick**
    * **Function:** gives the targeted players a TP Stick
    * **Description:** the TP Stick is detailed elsewhere
    * **Syntax:** /tpstick \<targets>
* **epreload**
    * **Function:** reloads this plugin's config file
    * **Description:** Reloads the data files for the EnderPlayers plugin.
    * **Syntax:** /epreload

### Items:

* **TP Stick**
    * **Purpose:** used to allow players with the enderteleport power to teleport
    * **Crafting:** this item is crafted using 2 sticks and an ender pearl.
        
        > <img src="./assets/tpstick_recipe.PNG" alt="TP Stick Recipe" width="150" height="150">
    
## Future Plans

**Note:** The exact time in which these will be completed has not been determined.

* ~~add player teleportation feature~~ *(completed)*
* ~~add player water damage feature~~ *(completed)*
* ~~add feature that allows enderplayers to safely look at the eyes of endermen~~ *(completed)*
* ~~add config file for plugin customization~~ *(completed)*