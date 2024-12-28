package com.inkapplications.coroutines.doubles

interface Organism

interface Animal: Organism
{
    object Dog: Animal
    object Cat: Animal
    object Bird: Animal
}

interface Plants: Organism
{
    object Tree: Plants
    object Flower: Plants
    object Grass: Plants
}
