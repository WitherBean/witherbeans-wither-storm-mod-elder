# Initialize steps
scoreboard players set @s steps 64

# Move the ray
execute at @s run function wwsm:ray/move

# Check if the ray hit a Creeper
execute as @s[tag=hitCreeper] at @s run function wwsm:ray/hit
execute as @s[tag=hitNotPlayer] at @s run function wwsm:ray/hit_player_not

# Destroy the ray (very important!)
kill @s
