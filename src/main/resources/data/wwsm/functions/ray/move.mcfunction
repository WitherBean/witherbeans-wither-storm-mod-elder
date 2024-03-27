# Move forward according to step size
tp @s ^ ^ ^0.5

# TEST
particle flame ~ ~ ~ 0 0 0 0 1 force

# Check for collisions with Creepers
execute if entity @e[type=!#wwsm:absorbable,distance=2..3,gamemode=!creative,gamemode=!spectator] run tag @s add hitCreeper
execute if entity @e[type=!#wwsm:absorbable,type=!player,distance=2..3,gamemode=!creative,gamemode=!spectator] run tag @s add hitNotPlayer

# Check for collisions with blocks
execute unless block ~ ~ ~ #wwsm:ray_permeable run tag @s add hitBlock

# Decrease the number of steps remaining
scoreboard players remove @s steps 1

# Recurse until we hit something or run out of steps
execute as @s[tag=!hitCreeper,tag=!hitBlock,scores={steps=1..}] at @s run function wwsm:ray/move
