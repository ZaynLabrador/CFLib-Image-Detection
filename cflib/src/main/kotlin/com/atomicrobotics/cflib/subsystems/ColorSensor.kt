package com.atomicrobotics.cflib.subsystems

import com.atomicrobotics.cflib.Command
import com.atomicrobotics.cflib.utilCommands.CustomCommand
import com.qualcomm.robotcore.hardware.NormalizedColorSensor
import com.qualcomm.robotcore.hardware.NormalizedRGBA
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap

class ColorSensor {

    enum class SleeveColor {
        RED,
        GREEN,
        BLUE
    }

    val gain = 18f
    private lateinit var sleeveColor: SleeveColor
    private lateinit var colors: NormalizedRGBA
    private lateinit var colorSensor: NormalizedColorSensor

    val read: Command
        get() = readSensor()

    fun initialize(){
        colorSensor.gain = gain
        colorSensor = hardwareMap.get(NormalizedColorSensor::class.java, "sensor_color")
        colors = colorSensor.normalizedColors
    }

    fun readSensor() = CustomCommand(_start ={
        if (colors.blue > colors.red && colors.blue > colors.green) {
            sleeveColor = SleeveColor.BLUE
        } else if (colors.green > colors.red && colors.green > colors.blue) {
            sleeveColor = SleeveColor.GREEN
        } else {
            sleeveColor = SleeveColor.RED
        }
    })
}