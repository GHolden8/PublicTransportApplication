package com.example.mapstest

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mapstest.BusStopsRepository // Corrected import
import com.example.mapstest.BusStop
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.text.SimpleDateFormat
import java.util.*

object BusStopsRepository {

    //Route 12
    //Starting New Approach to Problem
    private val RubyAndUmptanum = arrayOf(
        "07:32", "08:02", "08:32", "09:02", "09:32", "10:02", "10:32", "11:02", "11:32",
        "12:02", "12:32", "13:02", "13:32", "14:02", "14:32", "15:02", "15:32", "16:02",
        "16:32", "17:02", "17:32", "18:02", "18:32", "19:02", "19:32"
    )

    private val RubyAndManitoba = arrayOf(
        "07:29", "07:59", "08:29", "08:59", "09:29", "09:59", "10:29", "10:59", "11:29",
        "11:59", "12:29", "12:59", "13:29", "13:59", "14:29", "14:59", "15:29", "15:59",
        "16:29", "16:59", "17:29", "17:59", "18:29", "18:59", "19:29"
    )

    private val ManitobaAndPearl = arrayOf(
        "07:29", "07:59", "08:29", "08:59", "09:29", "09:59", "10:29", "10:59", "11:29",
        "11:59", "12:29", "12:59", "13:29", "13:59", "14:29", "14:59", "15:29", "15:59",
        "16:29", "16:59", "17:29", "17:59", "18:29", "18:59", "19:29"
    )

    private val WaterAndCapitol = arrayOf(
        "07:57", "08:27", "08:57", "09:27", "09:57", "10:27", "10:57", "11:27", "11:57",
        "12:27", "12:57", "13:27", "13:57", "14:27", "14:57", "15:27", "15:57", "16:27",
        "16:57", "17:27", "17:57", "18:27", "18:57", "19:27"
    )

    private val WaterAndCapitol2 = arrayOf(
        "07:34", "08:04", "08:34", "09:04", "09:34", "10:04", "10:34", "11:04", "11:34", "12:04",
        "12:34", "13:04", "13:34", "14:04", "14:34", "15:04", "15:34", "16:04", "16:34", "17:04",
        "17:34", "18:04", "18:34", "19:04", "19:34"
    )

    private val WaterAnd2nd = arrayOf(
        "07:35", "08:05", "08:35", "09:05", "09:35", "10:05", "10:35", "11:05", "11:35", "12:05",
        "12:35", "13:05", "13:35", "14:05", "14:35", "15:05", "15:35", "16:05", "16:35", "17:05",
        "17:35", "18:05", "18:35", "19:05"
    )

    private val WaterAnd2nd2 = arrayOf(
        "07:24", "07:54", "08:24", "08:54", "09:24", "09:54", "10:24", "10:54", "11:24", "11:54",
        "12:24", "12:54", "13:24", "13:54", "14:24", "14:54", "15:24", "15:54", "16:24", "16:54",
        "17:24", "17:54", "18:24", "18:54", "19:24"
    )

    private val ThirdAndRuby = arrayOf(
        "07:36", "08:06", "08:36", "09:06", "09:36", "10:06", "10:36", "11:06", "11:36", "12:06",
        "12:36", "13:06", "13:36", "14:06", "14:36", "15:06", "15:36", "16:06", "16:36", "17:06",
        "17:36", "18:06", "18:36", "19:06"
    )

    private val ThirdAndRuby2 = arrayOf(
        "07:52", "08:22", "08:52", "09:22", "09:52", "10:22", "10:52", "11:22", "11:52", "12:22",
        "12:52", "13:22", "13:52", "14:22", "14:52", "15:22", "15:52", "16:22", "16:52", "17:22",
        "17:52", "18:22", "18:52", "19:22"
    )

    private val RubyAnd4th = arrayOf(
        "07:52", "08:22", "08:52", "09:22", "09:52", "10:22", "10:52", "11:22", "11:52", "12:22",
        "12:52", "13:22", "13:52", "14:22", "14:52", "15:22", "15:52", "16:22", "16:52", "17:22",
        "17:52", "18:22", "18:52", "19:22"
    )

    private val RubyAnd4th2 = arrayOf(
        "07:40", "08:10", "08:40", "09:10", "09:40", "10:10", "10:40", "11:10", "11:40", "12:10",
        "12:40", "13:10", "13:40", "14:10", "14:40", "15:10", "15:40", "16:10", "16:40", "17:10",
        "17:40", "18:10", "18:40", "19:10"
    )

    private val ChestnutAndE6th = arrayOf(
        "07:41", "08:11", "08:41", "09:11", "09:41", "10:11", "10:41", "11:11", "11:41", "12:11",
        "12:41", "13:11", "13:41", "14:11", "14:41", "15:11", "15:41", "16:11", "16:41", "17:11",
        "17:41", "18:11", "18:41", "19:11"
    )

    private val ChestnutAndE6th2 = arrayOf(
        "07:43", "08:13", "08:43", "09:13", "09:43", "10:13", "10:43", "11:13", "11:43", "12:13",
        "12:43", "13:13", "13:43", "14:13", "14:43", "15:13", "15:43", "16:13", "16:43", "17:13",
        "17:43", "18:13", "18:43", "19:13"
    )

    private val UniversityAnd9th = arrayOf(
        "07:49", "08:19", "08:49", "09:19", "09:49", "10:19", "10:49", "11:19", "11:49", "12:19",
        "12:49", "13:19", "13:49", "14:19", "14:49", "15:19", "15:49", "16:19", "16:49", "17:19",
        "17:49", "18:19", "18:49", "19:19"
    )

    private val UniversityAnd9th2 = arrayOf(
        "07:41", "08:11", "08:41", "09:11", "09:41", "10:11", "10:41", "11:11", "11:41", "12:11",
        "12:41", "13:11", "13:41", "14:11", "14:41", "15:11", "15:41", "16:11", "16:41", "17:11",
        "17:41", "18:11", "18:41", "19:11"
    )

    private val E11thAndMaple = arrayOf(
        "07:40", "08:10", "08:40", "09:10", "09:40", "10:10", "10:40", "11:10", "11:40", "12:10",
        "12:40", "13:10", "13:40", "14:10", "14:40", "15:10", "15:40", "16:10", "16:40", "17:10",
        "17:40", "18:10", "18:40", "19:10"
    )

    private val AlderAnd14th = arrayOf(
        "07:47", "08:17", "08:47", "09:17", "09:47", "10:17", "10:47", "11:17", "11:47", "12:17",
        "12:47", "13:17", "13:47", "14:17", "14:47", "15:17", "15:47", "16:17", "16:47", "17:17",
        "17:47", "18:17", "18:47", "19:17"
    )

    private val AlderAnd14th2 = arrayOf(
        "07:52", "08:22", "08:52", "09:22", "09:52", "10:22", "10:52", "11:22", "11:52", "12:22",
        "12:52", "13:22", "13:52", "14:22", "14:52", "15:22", "15:52", "16:22", "16:52", "17:22",
        "17:52", "18:22", "18:52", "19:22"
    )

    private val AlderAndStudentVillage = arrayOf(
        "07:36", "08:06", "08:36", "09:06", "09:36", "10:06", "10:36", "11:06", "11:36", "12:06",
        "12:36", "13:06", "13:36", "14:06", "14:36", "15:06", "15:36", "16:06", "16:36", "17:06",
        "17:36", "18:06", "18:36", "19:06"
    )

    private val AlderAnd18th = arrayOf(
        "07:35", "08:05", "08:35", "09:05", "09:35", "10:05", "10:35", "11:05", "11:35", "12:05",
        "12:35", "13:05", "13:35", "14:05", "14:35", "15:05", "15:35", "16:05", "16:35", "17:05",
        "17:35", "18:05", "18:35", "19:05"
    )

    private val AlderAnd18th2 = arrayOf(
        "07:53", "08:23", "08:53", "09:23", "09:53", "10:23", "10:53", "11:23", "11:53", "12:23",
        "12:53", "13:23", "13:53", "14:23", "14:53", "15:23", "15:53", "16:23", "16:53", "17:23",
        "17:53", "18:23", "18:53", "19:23"
    )

    private val EighteenthAndBrooklane = arrayOf(
        "07:35", "08:05", "08:35", "09:05", "09:35", "10:05", "10:35", "11:05", "11:35", "12:05",
        "12:35", "13:05", "13:35", "14:05", "14:35", "15:05", "15:35", "16:05", "16:35", "17:05",
        "17:35", "18:05", "18:35", "19:05"
    )

    private val EighteenthAndBrooklane2 = arrayOf(
        "07:32", "08:02", "08:32", "09:02", "09:32", "10:02", "10:32", "11:02", "11:32", "12:02",
        "12:32", "13:02", "13:32", "14:02", "14:32", "15:02", "15:32", "16:02", "16:32", "17:02",
        "17:32", "18:02", "18:32", "19:02"
    )

    private val BrooklaneVillage = arrayOf(
        "07:33", "08:03", "08:33", "09:03", "09:33", "10:03", "10:33", "11:03", "11:33", "12:03",
        "12:33", "13:03", "13:33", "14:03", "14:33", "15:03", "15:33", "16:03", "16:33", "17:03",
        "17:33", "18:03", "18:33", "19:03"
    )

    private val AlderAndHelena = arrayOf(
        "07:53", "08:23", "08:53", "09:23", "09:53", "10:23", "10:53", "11:23", "11:53", "12:23",
        "12:53", "13:23", "13:53", "14:23", "14:53", "15:23", "15:53", "16:23", "16:53", "17:23",
        "17:53", "18:23", "18:53", "19:23"
    )

    private val AlderAndHelena2 = arrayOf(
        "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30",
        "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
        "18:00", "18:30", "19:00"
    )

    private val HelenaAndAirport = arrayOf(
        "07:55", "08:25", "08:55", "09:25", "09:55", "10:25", "10:55", "11:25", "11:55", "12:25",
        "12:55", "13:25", "13:55", "14:25", "14:55", "15:25", "15:55", "16:25", "16:55", "17:25",
        "17:55", "18:25", "18:55", "19:25"
    )

    private val AirportAndGreenfield = arrayOf(
        "08:25", "08:55", "09:25", "09:55", "10:25", "10:55", "11:25", "11:55", "12:25", "12:55",
        "13:25", "13:55", "14:25", "14:55", "15:25", "15:55", "16:25", "16:55", "17:25", "17:55",
        "18:25", "18:55", "19:25"
    )

    private val AlderAndWheaton = arrayOf(
        "08:27", "08:57", "09:27", "09:57", "10:27", "10:57", "11:27", "11:57", "12:27", "12:57",
        "13:27", "13:57", "14:27", "14:57", "15:27", "15:57", "16:27", "16:57", "17:27", "17:57",
        "18:27", "18:57", "19:27"
    )
    //End Route 12 Stops ^^^^

    //Route 13 Stops
    private val BeechAndElmview = arrayOf(
        "07:40", "08:37", "09:37", "10:37", "11:37", "12:37", "13:37", "14:37",
        "15:37", "16:37", "17:37", "18:37", "19:11"
    )


    private val MtnViewAndChestnutHearthstone = arrayOf(
        "07:26", "07:36", "07:46", "07:56", "08:06", "08:16", "08:26", "08:36", "08:46", "08:56",
        "09:06", "09:16", "09:26", "09:36", "09:46", "09:56", "10:06", "10:16", "10:26", "10:36",
        "10:46", "10:56", "11:06", "11:16", "11:26", "11:36", "11:46", "11:56", "12:06", "12:16",
        "12:26", "12:36", "12:46", "12:56", "13:06", "13:16", "13:26", "13:36", "13:46", "13:56",
        "14:06", "14:16", "14:26", "14:36", "14:46", "14:56", "15:06", "15:16", "15:26", "15:36",
        "15:46", "15:56", "16:06", "16:16", "16:26", "16:36", "16:46", "16:56", "17:06", "17:16",
        "17:26", "17:36", "17:46", "17:56", "18:06", "18:16", "18:26", "18:36", "18:46", "18:56",
        "19:06", "19:16", "19:26", "19:36"
    )

    private val MtnViewAndChestnutBriarwood = arrayOf(
        "07:20", "07:30", "07:40", "07:50", "08:00", "08:10", "08:20", "08:30", "08:40", "08:50",
        "09:00", "09:10", "09:20", "09:30", "09:40", "09:50", "10:00", "10:10", "10:20", "10:30",
        "10:40", "10:50", "11:00", "11:10", "11:20", "11:30", "11:40", "11:50", "12:00", "12:10",
        "12:20", "12:30", "12:40", "12:50", "13:00", "13:10", "13:20", "13:30", "13:40", "13:50",
        "14:00", "14:10", "14:20", "14:30", "14:40", "14:50", "15:00", "15:10", "15:20", "15:30",
        "15:40", "15:50", "16:00", "16:10", "16:20", "16:30", "16:40", "16:50", "17:00", "17:10",
        "17:20", "17:30", "17:40", "17:50", "18:00", "18:10", "18:20", "18:30", "18:40", "18:50",
        "19:00", "19:10", "19:20", "19:30", "19:40"
    )

    private val UrgentCare = arrayOf(
        "07:25", "07:35", "07:45", "07:55", "08:05", "08:15", "08:25", "08:35", "08:45", "08:55",
        "09:05", "09:15", "09:25", "09:35", "09:45", "09:55", "10:05", "10:15", "10:25", "10:35",
        "10:45", "10:55", "11:05", "11:15", "11:25", "11:35", "11:45", "11:55", "12:05", "12:15",
        "12:25", "12:35", "12:45", "12:55", "13:05", "13:15", "13:25", "13:35", "13:45", "13:55",
        "14:05", "14:15", "14:25", "14:35", "14:45", "14:55", "15:05", "15:15", "15:25", "15:35",
        "15:45", "15:55", "16:05", "16:15", "16:25", "16:35", "16:45", "16:55", "17:05", "17:15",
        "17:25", "17:35", "17:45", "17:55", "18:05", "18:15", "18:25", "18:35", "18:45", "18:55",
        "19:05", "19:15", "19:25", "19:35"
    )

    private val MtnViewAndWhitmanBiMart = arrayOf(
        "07:40", "08:33", "09:33", "10:33", "11:33", "12:33", "13:33", "14:33",
        "15:33", "16:33", "17:33", "18:33", "19:11"
    )

    private val MtnViewAndWhitmanWADOL = arrayOf(
        "07:48", "08:48", "09:48", "10:48", "11:48", "12:48", "13:48", "14:48",
        "15:48", "16:48", "17:48", "18:48"
    )

    private val EMountainViewAveAndRubyStreet = arrayOf(
        "07:48", "08:48", "09:48", "10:48", "11:48", "12:48", "13:48", "14:48",
        "15:48", "16:48", "17:48", "18:48"
    )

    private val EMtViewAndPineSuper1 = arrayOf(
        "07:48", "08:48", "09:48", "10:48", "11:48", "12:48", "13:48", "14:48",
        "15:48", "16:48", "17:48", "18:48"
    )

    private val KVHChestnutSeattle = arrayOf(
        "07:44", "08:44", "09:44", "10:44", "11:44", "12:44", "13:44", "14:44",
        "15:44", "16:44", "17:44", "18:44"
    )

    private val KVHChestnutSeattle2 = arrayOf(
        "07:40", "08:38", "09:38", "10:38", "11:38", "12:38", "13:38", "14:38",
        "15:38", "16:38", "17:38", "18:38"
    )

    private val ManitobaAndRuby13 = arrayOf(
        "07:40", "08:40", "09:40", "10:40", "11:40", "12:40", "13:40", "14:40",
        "15:40", "16:40", "17:40", "18:40"
    )

    private val CherryAndRuby13 = arrayOf(
        "07:39", "08:39", "09:39", "10:39", "11:39", "12:39", "13:39", "14:39",
        "15:39", "16:39", "17:39", "18:39"
    )

    private val WaterAndCapitolFredW = arrayOf(
        "07:40", "08:30", "09:30", "10:30", "11:30", "12:30", "13:30", "14:30",
        "15:30", "16:30", "17:30", "18:30"
    )

    private val WaterAndCapitolFredE = arrayOf(
        "07:51", "08:51", "09:51", "10:51", "11:51", "12:51", "13:51", "14:51",
        "15:51", "16:51", "17:51", "18:51"
    )

    private val WaterAnd2ndE = arrayOf(
        "07:51", "08:51", "09:51", "10:51", "11:51", "12:51", "13:51", "14:51",
        "15:51", "16:51", "17:51", "18:51"
    )

    private val WaterAnd2ndW13 = arrayOf(
        "07:34", "08:34", "09:34", "10:34", "11:34", "12:34", "13:34", "14:34",
        "15:34", "16:34", "17:34", "18:34"
    )

    private val ThirdAndRuby13 = arrayOf(
        "07:59", "08:59", "09:59", "10:59", "11:59", "12:59", "13:59", "14:59",
        "15:59", "16:59", "17:59", "18:59"
    )

    private val ThirdAndRuby13N = arrayOf(
        "08:25", "09:25", "10:25", "11:25", "12:25", "13:25", "14:25", "15:25",
        "16:25", "17:25", "18:25"
    )

    private val RubyAnd4thW13 = arrayOf(
        "08:25", "09:25", "10:25", "11:25", "12:25", "13:25", "14:25", "15:25",
        "16:25", "17:25", "18:25"
    )

    private val RubyAnd4thE13 = arrayOf(
        "08:57", "09:57", "10:57", "11:57", "12:57", "13:57", "14:57", "15:57",
        "16:57", "17:57", "18:57"
    )

    private val SpragueAnd7th13W = arrayOf(
        "08:19", "09:19", "10:19", "11:19", "12:19", "13:19", "14:19", "15:19",
        "16:19", "17:19", "18:19"
    )

    private val SpragueAnd7th13E = arrayOf(
        "08:59", "09:59", "10:59", "11:59", "12:59", "13:59", "14:59", "15:59",
        "16:59", "17:59", "18:59"
    )

    private val WildcatWayAnd11th13W = arrayOf(
        "08:18", "09:18", "10:18", "11:18", "12:18", "13:18", "14:18", "15:18",
        "16:18", "17:18", "18:18"
    )

    private val WildcatWayAnd11th13E = arrayOf(
        "08:01", "09:01", "10:01", "11:01", "12:01", "13:01", "14:01", "15:01",
        "16:01", "17:01", "18:01"
    )

    private val DeanAndNic13N = arrayOf(
        "08:17", "09:17", "10:17", "11:17", "12:17", "13:17", "14:17", "15:17",
        "16:17", "17:17", "18:17"
    )

    private val DeanAndNic13S = arrayOf(
        "08:02", "09:02", "10:02", "11:02", "12:02", "13:02", "14:02", "15:02",
        "16:02", "17:02", "18:02"
    )

    private val WalnutAnd18thW13 = arrayOf(
        "08:15", "09:15", "10:15", "11:15", "12:15", "13:15", "14:15", "15:15",
        "16:15", "17:15", "18:15"
    )

    private val WalnutAnd18thE13 = arrayOf(
        "08:04", "09:04", "10:04", "11:04", "12:04", "13:04", "14:04", "15:04",
        "16:04", "17:04", "18:04"
    )

    private val WalnutAndHelenaW13 = arrayOf(
        "08:15", "09:15", "10:15", "11:15", "12:15", "13:15", "14:15", "15:15",
        "16:15", "17:15", "18:15"
    )

    private val WalnutAndHelenaE13 = arrayOf(
        "08:05", "09:05", "10:05", "11:05", "12:05", "13:05", "14:05", "15:05",
        "16:05", "17:05", "18:05"
    )

    private val AirportAndGreenfield13 = arrayOf(
        "08:06", "09:06", "10:06", "11:06", "12:06", "13:06", "14:06", "15:06",
        "16:06", "17:06", "18:06"
    )

    private val AirportAndBender13 = arrayOf(
        "08:09", "09:09", "10:09", "11:09", "12:09", "13:09", "14:09", "15:09",
        "16:09", "17:09", "18:09"
    )

    //Route 13 End

    //Route 14 Start
    private val MountainViewAndMapleS = arrayOf(
        "08:08", "09:08", "10:08", "11:08", "12:08", "13:08", "14:08", "15:08",
        "16:08", "17:08", "18:08"
    )

    private val MountainViewAndMapleN = arrayOf(
        "08:13", "09:13", "10:13", "11:13", "12:13", "13:13", "14:13", "15:13",
        "16:13", "17:13", "18:13"
    )

    private val WillowAndSeattle = arrayOf(
        "08:12", "09:12", "10:12", "11:12", "12:12", "13:12", "14:12", "15:12",
        "16:12", "17:12", "18:12"
    )

    private val CapitolAndWillowEHS = arrayOf(
        "08:10", "09:10", "10:10", "11:10", "12:10", "13:10", "14:10", "15:10",
        "16:10", "17:10", "18:10"
    )

    private val MtnViewAndChestnut131 = arrayOf(
        "07:52", "08:07", "08:22", "08:37", "08:52", "09:07", "09:22", "09:37",
        "09:52", "10:07", "10:22", "10:37", "10:52", "11:07", "11:22", "11:37",
        "11:52", "12:07", "12:22", "12:37", "12:52", "13:07", "13:22", "13:37",
        "13:52", "14:07", "14:22", "14:37", "14:52", "15:07", "15:22", "15:37",
        "15:52", "16:07", "16:22", "16:37", "16:52", "17:07", "17:22", "17:37",
        "17:52", "18:07", "18:22", "18:37", "18:52", "19:07"
    )

    private val MtnViewAndChestnut132 = arrayOf(
        "07:52", "08:07", "08:22", "08:37", "08:52", "09:07", "09:22", "09:37",
        "09:52", "10:07", "10:22", "10:37", "10:52", "11:07", "11:22", "11:37",
        "11:52", "12:07", "12:22", "12:37", "12:52", "13:07", "13:22", "13:37",
        "13:52", "14:07", "14:22", "14:37", "14:52", "15:07", "15:22", "15:37",
        "15:52", "16:07", "16:22", "16:37", "16:52", "17:07", "17:22", "17:37",
        "17:52", "18:07", "18:22", "18:37", "18:52", "19:07"
    )

    private val UrgentCare13 = arrayOf(
        "07:51", "08:06", "08:21", "08:36", "08:51", "09:06", "09:21", "09:36",
        "09:51", "10:06", "10:21", "10:36", "10:51", "11:06", "11:21", "11:36",
        "11:51", "12:06", "12:21", "12:36", "12:51", "13:06", "13:21", "13:36",
        "13:51", "14:06", "14:21", "14:36", "14:51", "15:06", "15:21", "15:36",
        "15:51", "16:06", "16:21", "16:36", "16:51", "17:06", "17:21", "17:36",
        "17:51", "18:06", "18:21", "18:36", "18:51", "19:06"
    )

    private val MtnViewAndWhitmanBiMart14 = arrayOf(
        "08:03", "09:03", "10:03", "11:03", "12:03", "13:03", "14:03", "15:03",
        "16:03", "17:03", "18:03"
    )

    private val MtnViewAndWhitmanWADOL14 = arrayOf(
        "08:18", "09:18", "10:18", "11:18", "12:18", "13:18", "14:18", "15:18",
        "16:18", "17:18", "18:18"
    )

    private val EMtnViewAveAndRuby14 = arrayOf(
        "08:18", "09:18", "10:18", "11:18", "12:18", "13:18", "14:18", "15:18",
        "16:18", "17:18", "18:18"
    )

    private val EMtnViewAndPineSuper1 = arrayOf(
        "08:03", "09:03", "10:03", "11:03", "12:03", "13:03", "14:03", "15:03",
        "16:03", "17:03", "18:03"
    )

    private val WaterAndCapitolFredMeyerW14 = arrayOf(
        "08:01", "09:01", "10:01", "11:01", "12:01", "13:01", "14:01", "15:01",
        "16:01", "17:01", "18:01"
    )

    private val WaterAndCapitolE14 = arrayOf(
        "08:21", "09:21", "10:21", "11:21", "12:21", "13:21", "14:21", "15:21",
        "16:21", "17:21", "18:21"
    )

    private val WaterAnd2ndE14 = arrayOf(
        "07:58", "08:58", "09:58", "10:58", "11:58", "12:58", "13:58", "14:58",
        "15:58", "16:58", "17:58", "18:58"
    )

    private val WaterAnd2ndW14 = arrayOf(
        "07:58", "08:58", "09:58", "10:58", "11:58", "12:58", "13:58", "14:58",
        "15:58", "16:58", "17:58", "18:58"
    )

    private val ThirdAndRubyS14 = arrayOf(
        "08:24", "09:24", "10:24", "11:24", "12:24", "13:24", "14:24", "15:24",
        "16:24", "17:24", "18:24"
    )

    private val ThirdAndRubyN14 = arrayOf(
        "07:55", "08:55", "09:55", "10:55", "11:55", "12:55", "13:55", "14:55",
        "15:55", "16:55", "17:55", "18:55"
    )

    private val RubyAnd4thW14 = arrayOf(
        "07:55", "08:55", "09:55", "10:55", "11:55", "12:55", "13:55", "14:55",
        "15:55", "16:55", "17:55", "18:55"
    )

    private val RubyAnd4thE14 = arrayOf(
        "08:27", "09:27", "10:27", "11:27", "12:27", "13:27", "14:27", "15:27",
        "16:27", "17:27", "18:27"
    )

    private val SpragueAnd7thW14 = arrayOf(
        "08:45", "09:45", "10:45", "11:45", "12:45", "13:45", "14:45", "15:45",
        "16:45", "17:45", "18:45"
    )

    private val SpragueAnd7thE14 = arrayOf(
        "08:28", "09:28", "10:28", "11:28", "12:28", "13:28", "14:28", "15:28",
        "16:28", "17:28", "18:28"
    )

    private val WildcatWayAnd11thW14 = arrayOf(
        "08:43", "09:43", "10:43", "11:43", "12:43", "13:43", "14:43", "15:43",
        "16:43", "17:43", "18:43"
    )

    private val WildcatWayAnd11thE14 = arrayOf(
        "08:30", "09:30", "10:30", "11:30", "12:30", "13:30", "14:30", "15:30",
        "16:30", "17:30", "18:30"
    )

    private val DeanNicAndWalnutS14 = arrayOf(
        "08:31", "09:31", "10:31", "11:31", "12:31", "13:31", "14:31", "15:31",
        "16:31", "17:31", "18:31"
    )

    private val DeanNicAndWalnutN14 = arrayOf(
        "08:42", "09:42", "10:42", "11:42", "12:42", "13:42", "14:42", "15:42",
        "16:42", "17:42", "18:42"
    )

    private val WalnutAnd18thW14 = arrayOf(
        "08:40", "09:40", "10:40", "11:40", "12:40", "13:40", "14:40", "15:40",
        "16:40", "17:40", "18:40"
    )

    private val WalnutAnd18thE14 = arrayOf(
        "08:32", "09:32", "10:32", "11:32", "12:32", "13:32", "14:32", "15:32",
        "16:32", "17:32", "18:32"
    )

    private val WalnutAndHelenaW14 = arrayOf(
        "08:40", "09:40", "10:40", "11:40", "12:40", "13:40", "14:40", "15:40",
        "16:40", "17:40", "18:40"
    )

    private val WalnutAndHelenaE14 = arrayOf(
        "08:33", "09:33", "10:33", "11:33", "12:33", "13:33", "14:33", "15:33",
        "16:33", "17:33", "18:33"
    )

    private val HelenaAndBrooksfield14 = arrayOf(
        "08:34", "09:34", "10:34", "11:34", "12:34", "13:34", "14:34", "15:34",
        "16:34", "17:34", "18:34"
    )

    private val WaterAndHelena14 = arrayOf(
        "08:35", "09:35", "10:35", "11:35", "12:35", "13:35", "14:35", "15:35",
        "16:35", "17:35", "18:35"
    )

    private val WaterAndGreenfield14 = arrayOf(
        "08:36", "09:36", "10:36", "11:36", "12:36", "13:36", "14:36", "15:36",
        "16:36", "17:36", "18:36"
    )

    private val WaterAnd26th14 = arrayOf(
        "08:36", "09:36", "10:36", "11:36", "12:36", "13:36", "14:36", "15:36",
        "16:36", "17:36", "18:36"
    )

    private val BenderAndEllington14 = arrayOf(
        "08:38", "09:38", "10:38", "11:38", "12:38", "13:38", "14:38", "15:38",
        "16:38", "17:38", "18:38"
    )
/*
       Route 15 arrival and departure times
    */
    // Direction (Ruby -> I-90 Love's)
    private val RubyAnd4thTwo15 = arrayOf(
        "08:21", "09:21", "10:21", "11:21", "12:21", "13:21", "14:21", "15:21",
        "16:21", "17:21", "18:21", "19:21"
    )
    private val ThirdAndRubyTwo15 = arrayOf(
        "08:20", "09:20", "10:20", "11:20", "12:20", "13:20", "14:20", "15:20",
        "16:20", "17:20", "18:20", "19:20"
    )
    private val FifthAndKittitasTwo15 = arrayOf(
        "08:18", "09:18", "10:18", "11:18", "12:18", "13:18", "14:18", "15:18",
        "16:18", "17:18", "18:18", "19:18"
    )
    private val HopeSourceTwo15 = arrayOf(
        "08:17", "09:17", "10:17", "11:17", "12:17", "13:17", "14:17", "15:17",
        "16:17", "17:17", "18:17", "19:17"
    )
    private val FifthAndPacificTwo15 = arrayOf(
        "08:15", "09:15", "10:15", "11:15", "12:15", "13:15", "14:15", "15:15",
        "16:15", "17:15", "18:15", "19:15"
    )
    
    // Direction (Ruby <- I-90 Love's)
    private val I90WestInterchange15 = arrayOf(
        "08:11", "09:11", "10:11", "11:11", "12:11", "13:11", "14:11", "15:11",
        "16:11", "17:11", "18:11", "19:11"
    )
    private val DolorwayAndLakeshore15 = arrayOf(
        "08:09", "09:09", "10:09", "11:09", "12:09", "13:09", "14:09", "15:09",
        "16:09", "17:09", "18:09", "19:09"
    )
    private val DavitaDialysis15 = arrayOf(
        "08:08", "09:08", "10:08", "11:08", "12:08", "13:08", "14:08", "15:08",
        "16:08", "17:08", "18:08", "19:08"
    )
    private val DolorwayAtProspect15 = arrayOf(
        "08:07", "09:07", "10:07", "11:07", "12:07", "13:07", "14:07", "15:07",
        "16:07", "17:07", "18:07", "19:07"
    )
    private val FifthAndPacific15 = arrayOf(
        "08:06", "09:06", "10:06", "11:06", "12:06", "13:06", "14:06", "15:06",
        "16:06", "17:06", "18:06", "19:06"
    )
    private val HopeSource15 = arrayOf(
        "08:04", "09:04", "10:04", "11:04", "12:04", "13:04", "14:04", "15:04",
        "16:04", "17:04", "18:04", "19:04"
    )
    private val FifthAndKittitas15 = arrayOf(
        "08:02", "09:02", "10:02", "11:02", "12:02", "13:02", "14:02", "15:02",
        "16:02", "17:02", "18:02", "19:02"
    )
    private val ThirdAndRuby15 = arrayOf(
        "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00",
        "16:00", "17:00", "18:00", "19:00"
    )
    private val FourthAndRuby15 = arrayOf(
        "07:58", "08:58", "09:58", "10:58", "11:58", "12:58", "13:58", "14:58", "15:58",
        "16:58", "17:58", "18:58"
    )
    /*
        Route 16 arrival and departure times
     */
    // Direction (I-90 Love's -> Ruby)
    private val RubyAnd4th16 = arrayOf(
        "07:50", "08:50", "09:50", "10:50", "11:50", "12:50", "13:50", "14:50",
        "15:50", "16:50", "17:50"
    )

    private val SpragueAnd7th16 = arrayOf(
        "07:48", "08:48", "09:48", "10:48", "11:48", "12:48", "13:48", "14:48",
        "15:48", "16:48", "17:48"
    )

    private val WildcatWayAnd11th16 = arrayOf(
        "07:46", "08:46", "09:46", "10:46", "11:46", "12:46", "13:46", "14:46",
        "15:46", "16:46", "17:46"
    )

    private val WildcatWayAnd14th16 = arrayOf(
        "07:45", "08:45", "09:45", "10:45", "11:45", "12:45", "13:45", "14:45",
        "15:45", "16:45", "17:45"
    )

    private val BStAndWildcatWay16 = arrayOf(
        "07:45", "08:45", "09:45", "10:45", "11:45", "12:45", "13:45", "14:45",
        "15:45", "16:45", "17:45"
    )

    private val CoralAndRainer16 = arrayOf(
        "07:43", "08:43", "09:43", "10:43", "11:43", "12:43", "13:43", "14:43",
        "15:43", "16:43", "17:43"
    )

    private val CoralAndUniversityWay16 = arrayOf(
        "07:42", "08:42", "09:42", "10:42", "11:42", "12:42", "13:42", "14:42",
        "15:42", "16:42", "17:42"
    )

    private val UniversityWayDSHS16 = arrayOf(
        "07:41", "08:41", "09:41", "10:41", "11:41", "12:41", "13:41", "14:41",
        "15:41", "16:41", "17:41"
    )

    private val CritterCareOnUniversity16 = arrayOf(
        "07:39", "08:39", "09:39", "10:39", "11:39", "12:39", "13:39", "14:39",
        "15:39", "16:39", "17:39"
    )

    private val I90WestInterchange16 = arrayOf(
        "07:38", "08:38", "09:38", "10:38", "11:38", "12:38", "13:38", "14:38",
        "15:38", "16:38", "17:38"
    )

    // Other Direction (Ruby -> I-90 Love's)
    private val SpragueAnd7thTwo16 = arrayOf(
        "07:27", "08:27", "09:27", "10:27", "11:27", "12:27", "13:27", "14:27",
        "15:27", "16:27", "17:27", "18:27"
    )

    private val RubyAnd4thTwo16 = arrayOf(
        "07:25", "08:25", "09:25", "10:25", "11:25", "12:25", "13:25", "14:25",
        "15:25", "16:25", "17:25", "18:25"
    )

    private val WildcatWayAnd11thTwo16 = arrayOf(
        "07:28", "08:28", "09:28", "10:28", "11:28", "12:28", "13:28", "14:28",
        "15:28", "16:28", "17:28", "18:28"
    )

    private val WildcatWayAnd14thTwo16 = arrayOf(
        "07:30", "08:30", "09:30", "10:30", "11:30", "12:30", "13:30", "14:30",
        "15:30", "16:30", "17:30", "18:30"
    )

    private val BStAndWildcatWayTwo16 = arrayOf(
        "07:30", "08:30", "09:30", "10:30", "11:30", "12:30", "13:30", "14:30",
        "15:30", "16:30", "17:30", "18:30"
    )

    private val CoralAnd15th16 = arrayOf(
        "07:33", "08:33", "09:33", "10:33", "11:33", "12:33", "13:33", "14:33",
        "15:33", "16:33", "17:33", "18:33"
    )

    private val UniversityWayWestBound16 = arrayOf(
        "07:35", "08:35", "09:35", "10:35", "11:35", "12:35", "13:35", "14:35",
        "15:35", "16:35", "17:35", "18:35"
    )

    private fun getNextFiveTimes(times: Array<String>): List<String> {
        val timeZone = TimeZone.getTimeZone("America/Los_Angeles")
        val currentTime = Calendar.getInstance(timeZone).time
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        formatter.timeZone = timeZone
        val formattedCurrentTime = formatter.format(currentTime)
        val parsedCurrentTime = formatter.parse(formattedCurrentTime)

        println("Current Time: $formattedCurrentTime")

        val parsedTimes = times.map { formatter.parse(it) to it }

        val upcomingTimes = parsedTimes
            .filter { it.first.after(parsedCurrentTime) }
            .take(5)
            .map { it.second }

        // Debug logging
        println("Parsed Times: ${parsedTimes.joinToString { it.second }}")
        println("Upcoming Times: ${upcomingTimes.joinToString()}")

        return upcomingTimes
    }
    

    fun getBusStopsForRoute(route: Int): List<BusStop> {
        return when (route) {
            12 -> listOf(
                BusStop(LatLng(46.98132, -120.54465), "Ruby and Umptanum Stop", "Next Arrivals: ${getNextFiveTimes(RubyAndUmptanum).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //1 - ID: 332
                BusStop(LatLng(46.98757, -120.54460), "Ruby and Manitoba Stop", "Next Arrivals: ${getNextFiveTimes(RubyAndManitoba).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //2 - ID: 328
                BusStop(LatLng(46.98820, -120.54698), "Manitoba and Pearl Stop - 1", "Next Arrivals: ${getNextFiveTimes(ManitobaAndPearl).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //3 - ID: 288
                BusStop(LatLng(46.98820, -120.54698), "Ruby and Manitoba Stop - 2", "List of arrivals and departures", BitmapDescriptorFactory.HUE_GREEN, 12), //4 - ID: 388
                BusStop(LatLng(46.99106, -120.54986), "Water and Capitol Stop - Fred Meyer - 1", "Next Arrivals: ${getNextFiveTimes(WaterAndCapitol2).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //5 - ID: 392
                BusStop(LatLng(46.99369, -120.54975), "Water & 2nd - 1", "Next Arrivals: ${getNextFiveTimes(WaterAnd2nd).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //6 - ID: 380
                BusStop(LatLng(46.99370, -120.55002), "Water and 2nd - 2", "Next Arrivals: ${getNextFiveTimes(WaterAnd2nd2).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //7 - ID: 384
                BusStop(LatLng(46.99444, -120.54494), "3rd and Ruby - 1", "Next Arrivals: ${getNextFiveTimes(ThirdAndRuby).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //8 - ID: 124
                BusStop(LatLng(46.99459, -120.54476), "3rd and Ruby - 2", "Next Arrivals: ${getNextFiveTimes(ThirdAndRuby2).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //9 - ID: 128
                BusStop(LatLng(46.99577, -120.54472), "Ruby and 4th(Downtown) - 1", "Next Arrivals: ${getNextFiveTimes(RubyAnd4th).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //10 - ID: 320
                BusStop(LatLng(46.99583, -120.54446), "Ruby and 4th(Downtown) - 2", "Next Arrivals: ${getNextFiveTimes(RubyAnd4th2).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //11 - ID: 324
                BusStop(LatLng(46.99801, -120.53716), "Chestnut and E 6th - 1", "Next Arrivals: ${getNextFiveTimes(ChestnutAndE6th).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //12 - ID: 212
                BusStop(LatLng(46.99802, -120.53744), "Chestnut and E 6th - 2", "Next Arrivals: ${getNextFiveTimes(ChestnutAndE6th2).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //13 - ID: 208
                BusStop(LatLng(47.00062, -120.53594), "University and 9th - 1", "Next Arrivals: ${getNextFiveTimes(UniversityAnd9th).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //14 - ID: 344
                BusStop(LatLng(47.00131, -120.53510), "University and 9th - 2", "Next Arrivals: ${getNextFiveTimes(UniversityAnd9th2).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //15 - ID: 348
                BusStop(LatLng(47.00303, -120.53512), "E 11th & N Maple (SURC)", "Next Arrivals: ${getNextFiveTimes(E11thAndMaple).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //16 - ID: 248
                BusStop(LatLng(47.00596, -120.53202), "Alder & 14th - 1", "Next Arrivals: ${getNextFiveTimes(AlderAnd14th).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //17 - ID: 156
                BusStop(LatLng(47.00650, -120.53179), "Alder and 14th- 2", "Next Arrivals: ${getNextFiveTimes(AlderAnd14th2).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //18 - ID: 160
                BusStop(LatLng(47.00791, -120.53205), "Alder and Student Village", "Next Arrivals: ${getNextFiveTimes(AlderAndStudentVillage).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //19 - ID: 180
                BusStop(LatLng(47.01028, -120.53190), "Alder and 18th - 1", "Next Arrivals: ${getNextFiveTimes(AlderAnd18th).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //20 - ID: 164
                BusStop(LatLng(47.01026, -120.53166), "Alder and 18th - 2", "Next Arrivals: ${getNextFiveTimes(AlderAnd18th2).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //21 - ID: 168
                BusStop(LatLng(47.01068, -120.52595), "18th and Brooklane - 1", "Next Arrivals: ${getNextFiveTimes(EighteenthAndBrooklane).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //22 - ID: 120
                BusStop(LatLng(47.01051, -120.52594), "18th and Brooklane - 2", "Next Arrivals: ${getNextFiveTimes(EighteenthAndBrooklane2).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //23 - ID: 116
                BusStop(LatLng(47.01226, -120.52289), "Brooklane Village", "Next Arrivals: ${getNextFiveTimes(BrooklaneVillage).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //24 - ID: 196
                BusStop(LatLng(47.01350, -120.53165), "Alder and Helena", "Next Arrivals: ${getNextFiveTimes(AlderAndHelena).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //25 - ID: 172
                BusStop(LatLng(47.01351, -120.53193), "Alder and Helena 2", "Next Arrivals: ${getNextFiveTimes(AlderAndHelena2).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //26 - ID: 176
                BusStop(LatLng(47.01405, -120.53911), "Helena and Airport", "Next Arrivals: ${getNextFiveTimes(HelenaAndAirport).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //27 - ID: 264
                BusStop(LatLng(47.01776, -120.53968), "Airport and Greenfield(Verge)", "Next Arrivals: ${getNextFiveTimes(AirportAndGreenfield).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //28 - ID: 152
                BusStop(LatLng(47.01658, -120.53193), "Alder and Wheaton", "Next Arrivals: ${getNextFiveTimes(AlderAndWheaton).joinToString(", ")}", BitmapDescriptorFactory.HUE_GREEN, 12), //29 - ID: 184
            )
            13 -> listOf(
                BusStop(LatLng(47.02722, -120.53664), "Beech & Elmview", "Next Arrivals: ${getNextFiveTimes(BeechAndElmview).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //1 - ID: 188
                BusStop(LatLng(46.98378, -120.53620), "Mtn View & Chestnut (Briarwood)", "Next Arrivals: ${getNextFiveTimes(MtnViewAndChestnutBriarwood).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //2 - ID: 304
                BusStop(LatLng(46.98407, -120.53866), "Urgent Care", "Next Arrivals: ${getNextFiveTimes(UrgentCare).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //3 - ID: 272
                BusStop(LatLng(46.98316, -120.53780), "Mtn View & Chestnut (Hearthstone)", "Next Arrivals: ${getNextFiveTimes(MtnViewAndChestnutHearthstone).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //4 - ID: 308
                BusStop(LatLng(46.98447, -120.54121), "Mtn View & Whitman (Bi-Mart)", "Next Arrivals: ${getNextFiveTimes(MtnViewAndWhitmanBiMart).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //5 - ID: 312
                BusStop(LatLng(46.98469, -120.54142), "Mtn View & Whitman (WA DOL)", "Next Arrivals: ${getNextFiveTimes(MtnViewAndWhitmanWADOL).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //6 - ID: 316
                BusStop(LatLng(46.98466, -120.54489), "E Mountain View Ave and Ruby St", "Next Arrivals: ${getNextFiveTimes(EMountainViewAveAndRubyStreet).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //7 - ID: 256
                BusStop(LatLng(46.98451, -120.54488), "E Mt View & Pine (Super 1)", "Next Arrivals: ${getNextFiveTimes(EMtViewAndPineSuper1).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //8 - ID: 260
                BusStop(LatLng(46.98763, -120.53666), "KVH (Chestnut @ Seattle) E", "Next Arrivals: ${getNextFiveTimes(KVHChestnutSeattle2).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //9 - ID: 280
                BusStop(LatLng(46.98745, -120.53687), "KVH (Chestnut @ Seattle W", "Next Arrivals: ${getNextFiveTimes(KVHChestnutSeattle).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //10 - ID: 284
                BusStop(LatLng(46.98824, -120.54370), "Manitoba & Ruby", "Next Arrivals: ${getNextFiveTimes(ManitobaAndRuby13).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //11 - ID: 416
                BusStop(LatLng(46.98696, -120.54364), "Cherry & Ruby", "Next Arrivals: ${getNextFiveTimes(CherryAndRuby13).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //12 - ID: 204
                BusStop(LatLng(46.99102, -120.54994), "Water & Capitol (Fred Meyer) W", "Next Arrivals: ${getNextFiveTimes(WaterAndCapitolFredW).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //13 - ID: 388
                BusStop(LatLng(46.99147, -120.54964), "Water & Capitol (Fred Meyer) E", "Next Arrivals: ${getNextFiveTimes(WaterAndCapitolFredE).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //14 - ID: 392
                BusStop(LatLng(46.99366, -120.54977), "Water & 2nd E", "Next Arrivals: ${getNextFiveTimes(WaterAnd2ndE).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //15 - ID: 380
                BusStop(LatLng(46.99372, -120.55000), "Water & 2nd W", "Next Arrivals: ${getNextFiveTimes(WaterAnd2ndW13).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //16 - ID: 384
                BusStop(LatLng(46.99443, -120.54493), "3rd & Ruby S", "Next Arrivals: ${getNextFiveTimes(ThirdAndRuby13).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //17 - ID: 124
                BusStop(LatLng(46.99459, -120.54477), "3rd & Ruby N", "Next Arrivals: ${getNextFiveTimes(ThirdAndRuby13N).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //18 - ID: 128
                BusStop(LatLng(46.99579, -120.54471), "Ruby & 4th (Downtown) W", "Next Arrivals: ${getNextFiveTimes(RubyAnd4thW13).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //19 - ID: 320
                BusStop(LatLng(46.99582, -120.54445), "Ruby & 4th (Downtown) E", "Next Arrivals: ${getNextFiveTimes(RubyAnd4thE13).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //20 - ID: 324
                BusStop(LatLng(46.99889, -120.54348), "Sprague & 7th W", "Next Arrivals: ${getNextFiveTimes(SpragueAnd7th13W).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //21 - ID: 336
                BusStop(LatLng(46.99889, -120.54322), "Sprague & 7th E", "Next Arrivals: ${getNextFiveTimes(SpragueAnd7th13E).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //22 - ID: 340
                BusStop(LatLng(47.00355, -120.54345), "Wildcat Way & 11th W", "Next Arrivals: ${getNextFiveTimes(WildcatWayAnd11th13W).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //23 - ID: 408
                BusStop(LatLng(47.00354, -120.54321), "Wildcat Way & 11th E", "Next Arrivals: ${getNextFiveTimes(WildcatWayAnd11th13E).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //24 - ID: 404
                BusStop(LatLng(47.00607, -120.54076), "Dean Nic & Wildcat Wy (Brooks Library) N", "Next Arrivals: ${getNextFiveTimes(DeanAndNic13N).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //25 - ID: 252
                BusStop(LatLng(47.00607, -120.54079), "Dean Nic & Wildcat Wy (Brooks Library) S", "Next Arrivals: ${getNextFiveTimes(DeanAndNic13S).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //26 - ID: 236
                BusStop(LatLng(47.01091, -120.53997), "Walnut & 18th W", "Next Arrivals: ${getNextFiveTimes(WalnutAnd18thW13).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //27 - ID: 364
                BusStop(LatLng(47.01103, -120.53952), "Walnut & 18th E", "Next Arrivals: ${getNextFiveTimes(WalnutAnd18thE13).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //28 - ID: 360
                BusStop(LatLng(47.01375, -120.53991), "Walnut & Helena W", "Next Arrivals: ${getNextFiveTimes(WalnutAndHelenaW13).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //29 - ID: 368
                BusStop(LatLng(47.01378, -120.53967), "Walnut & Helena E", "Next Arrivals: ${getNextFiveTimes(WalnutAndHelenaE13).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //30 - ID: 372
                BusStop(LatLng(47.01776, -120.53969), "Airport & Greenfield (Verge)", "Next Arrivals: ${getNextFiveTimes(AirportAndGreenfield13).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //31 - ID: 152
                BusStop(LatLng(47.02146, -120.53988), "Airport & Bender", "Next Arrivals: ${getNextFiveTimes(AirportAndBender13).joinToString(", ")}", BitmapDescriptorFactory.HUE_YELLOW, 13), //32 - ID: 148
            )
            14 -> listOf(
                BusStop(LatLng(46.98452, -120.53256), "Mountain View & Maple S", "Next Arrivals: ${getNextFiveTimes(MountainViewAndMapleS).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //1 - ID: 296
                BusStop(LatLng(46.98471, -120.53252), "Mountain View & Maple N", "Next Arrivals: ${getNextFiveTimes(MountainViewAndMapleN).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //2 - ID: 300
                BusStop(LatLng(46.98742, -120.52880), "Willow & Seattle", "Next Arrivals: ${getNextFiveTimes(WillowAndSeattle).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //3 - ID: 412
                BusStop(LatLng(46.99190, -120.52752), "Capitol & Willow (EHS)", "Next Arrivals: ${getNextFiveTimes(CapitolAndWillowEHS).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //4 - ID: 200
                BusStop(LatLng(46.98378, -120.53620), "Mtn View & Chestnut", "Next Arrivals: ${getNextFiveTimes(MtnViewAndChestnut132).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //5 - ID: 304
                BusStop(LatLng(46.98407, -120.53866), "Urgent Care", "Next Arrivals: ${getNextFiveTimes(UrgentCare13).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //6 - ID: 272
                BusStop(LatLng(46.98316, -120.53780), "Mtn View & Chestnut (Briarwood)", "Next Arrivals: ${getNextFiveTimes(MtnViewAndChestnut131).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //7 - ID: 308
                BusStop(LatLng(46.98447, -120.54121), "Mtn View & Whitman (Bi-Mart)", "Next Arrivals: ${getNextFiveTimes(MtnViewAndWhitmanBiMart14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //8 - ID: 312
                BusStop(LatLng(46.98469, -120.54142), "Mtn View & Whitman (WA DOL)", "Next Arrivals: ${getNextFiveTimes(MtnViewAndWhitmanWADOL14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //9 - ID: 316
                BusStop(LatLng(46.98466, -120.54489), "E Mountain View Ave and Ruby St", "Next Arrivals: ${getNextFiveTimes(EMtnViewAveAndRuby14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //10 - ID: 256
                BusStop(LatLng(46.98451, -120.54488), "E Mt View & Pine (Super 1)", "Next Arrivals: ${getNextFiveTimes(EMtnViewAndPineSuper1).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //11 - ID: 260
                BusStop(LatLng(46.99102, -120.54994), "Water & Capitol (Fred Meyer) W", "Next Arrivals: ${getNextFiveTimes(WaterAndCapitolFredMeyerW14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //12 - ID: 388
                BusStop(LatLng(46.99147, -120.54964), "Water & Capitol (Fred Meyer) E", "Next Arrivals: ${getNextFiveTimes(WaterAndCapitolE14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //13 - ID: 392
                BusStop(LatLng(46.99366, -120.54977), "Water & 2nd E", "Next Arrivals: ${getNextFiveTimes(WaterAnd2ndE14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //14 - ID: 380
                BusStop(LatLng(46.99372, -120.55000), "Water & 2nd W", "Next Arrivals: ${getNextFiveTimes(WaterAnd2ndW14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //15 - ID: 384
                BusStop(LatLng(46.99443, -120.54493), "3rd & Ruby S", "Next Arrivals: ${getNextFiveTimes(ThirdAndRubyS14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //16 - ID: 124
                BusStop(LatLng(46.99459, -120.54477), "3rd & Ruby N", "Next Arrivals: ${getNextFiveTimes(ThirdAndRubyN14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //17 - ID: 128
                BusStop(LatLng(46.99579, -120.54471), "Ruby & 4th (Downtown) W", "Next Arrivals: ${getNextFiveTimes(RubyAnd4thW14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //18 - ID: 320
                BusStop(LatLng(46.99582, -120.54445), "Ruby & 4th (Downtown) E", "Next Arrivals: ${getNextFiveTimes(RubyAnd4thE14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //19 - ID: 324
                BusStop(LatLng(46.99889, -120.54348), "Sprague & 7th W", "Next Arrivals: ${getNextFiveTimes(SpragueAnd7thW14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //20 - ID: 336
                BusStop(LatLng(46.99889, -120.54322), "Sprague & 7th E", "Next Arrivals: ${getNextFiveTimes(SpragueAnd7thE14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //21 - ID: 340
                BusStop(LatLng(47.00355, -120.54345), "Wildcat Way & 11th W", "Next Arrivals: ${getNextFiveTimes(WildcatWayAnd11thW14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //22 - ID: 408
                BusStop(LatLng(47.00354, -120.54321), "Wildcat Way & 11th E", "Next Arrivals: ${getNextFiveTimes(WildcatWayAnd11thE14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //23 - ID: 404
                BusStop(LatLng(47.00607, -120.54076), "Dean Nic & Wildcat Wy (Brooks Library) N", "Next Arrivals: ${getNextFiveTimes(DeanNicAndWalnutN14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //24 - ID: 252
                BusStop(LatLng(47.00607, -120.54079), "Dean Nic & Wildcat Wy (Brooks Library) S", "Next Arrivals: ${getNextFiveTimes(DeanNicAndWalnutS14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //25 - ID: 236
                BusStop(LatLng(47.01091, -120.53997), "Walnut & 18th W", "Next Arrivals: ${getNextFiveTimes(WalnutAnd18thW14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //26 - ID: 364
                BusStop(LatLng(47.01103, -120.53952), "Walnut & 18th E", "Next Arrivals: ${getNextFiveTimes(WalnutAnd18thE14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //27 - ID: 360
                BusStop(LatLng(47.01375, -120.53991), "Walnut & Helena W", "Next Arrivals: ${getNextFiveTimes(WalnutAndHelenaW14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //28 - ID: 368
                BusStop(LatLng(47.01378, -120.53967), "Walnut & Helena E", "Next Arrivals: ${getNextFiveTimes(WalnutAndHelenaE14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //29 - ID: 372
                BusStop(LatLng(47.01404, -120.54336), "Helena & Brooksfield", "Next Arrivals: ${getNextFiveTimes(HelenaAndBrooksfield14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //30 - ID: 268
                BusStop(LatLng(47.01420, -120.55014), "Water & Helena", "Next Arrivals: ${getNextFiveTimes(WaterAndHelena14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //31 - ID:  396
                BusStop(LatLng(47.01724, -120.55022), "Water @ Greenfield", "Next Arrivals: ${getNextFiveTimes(WaterAndGreenfield14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //32 - ID:  400
                BusStop(LatLng(47.02025, -120.55023), "Water & 26th", "Next Arrivals: ${getNextFiveTimes(WaterAnd26th14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //33 - ID:  376
                BusStop(LatLng(47.02115, -120.54799), "Bender & Ellington", "Next Arrivals: ${getNextFiveTimes(BenderAndEllington14).joinToString(", ")}", BitmapDescriptorFactory.HUE_CYAN, 14), //34 - ID:  192
            )
            15 -> listOf(
                // ... add all stops for route 15
                BusStop(LatLng(46.995750819023456, -120.54472041778119), "Ruby & 4th E", "Next Arrivals: ${getNextFiveTimes(RubyAnd4thTwo15).joinToString(", ")}", BitmapDescriptorFactory.HUE_ORANGE, 15), //29 - ID: 184
                BusStop(LatLng(46.9958157369205, -120.54446364914462), "Ruby & 4th W", "Next Arrivals: ${getNextFiveTimes(FourthAndRuby15).joinToString(", ")}", BitmapDescriptorFactory.HUE_ORANGE, 15), //29 - ID: 184
                BusStop(LatLng(46.9948070874003, -120.54478601727311), "3rd & Ruby-1", "Next Arrivals: ${getNextFiveTimes(ThirdAndRuby15).joinToString(", ")}", BitmapDescriptorFactory.HUE_ORANGE, 15), //29 - ID: 184
                BusStop(LatLng(46.99449942618953, -120.5449774308591), "3rd & Ruby-2", "Next Arrivals: ${getNextFiveTimes(ThirdAndRubyTwo15).joinToString(", ")}", BitmapDescriptorFactory.HUE_ORANGE, 15), //29 - ID: 184
                BusStop(LatLng(46.99666907463079, -120.55176427986413), "5th & Kittitas -1 ", "Next Arrivals: ${getNextFiveTimes(FifthAndKittitas15).joinToString(", ")}", BitmapDescriptorFactory.HUE_ORANGE, 15), //29 - ID: 184
                BusStop(LatLng(46.99642408222544, -120.55122195220088), "5th & Kittitas -2 ", "Next Arrivals: ${getNextFiveTimes(FifthAndKittitasTwo15).joinToString(", ")}", BitmapDescriptorFactory.HUE_ORANGE, 15), //29 - ID: 184
                BusStop(LatLng(46.9964063788581, -120.56104179607546), "5th & Pacific", "Next Arrivals: ${getNextFiveTimes(FifthAndPacific15).joinToString(", ")}", BitmapDescriptorFactory.HUE_ORANGE, 15), //29 - ID: 184
                BusStop(LatLng(47.00227501104512, -120.57913362446341), "Davita Dialysis", "Next Arrivals: ${getNextFiveTimes(DavitaDialysis15).joinToString(", ")}", BitmapDescriptorFactory.HUE_ORANGE, 15), //29 - ID: 184
                BusStop(LatLng(47.00413873557294, -120.5851892689678), "Dolarway & Lakeshore", "Next Arrivals: ${getNextFiveTimes(DolorwayAndLakeshore15).joinToString(", ")}", BitmapDescriptorFactory.HUE_ORANGE, 15), //29 - ID: 184
                BusStop(LatLng(46.99422892848124, -120.5549163647288), "3rd & Wenas", "Next Arrivals: ${getNextFiveTimes(HopeSource15).joinToString(", ")}", BitmapDescriptorFactory.HUE_ORANGE, 15), //29 - ID: 184
                BusStop(LatLng(46.99962966868825, -120.5652094156598), "Dolarway & Prospect", "Next Arrivals: ${getNextFiveTimes(DolorwayAtProspect15).joinToString(", ")}", BitmapDescriptorFactory.HUE_ORANGE, 15), //29 - ID: 184
                BusStop(LatLng(47.00744466822952, -120.58633554818654), "I-90 West Interchange", "Next Arrivals: ${getNextFiveTimes(I90WestInterchange15).joinToString(", ")}", BitmapDescriptorFactory.HUE_ORANGE, 15), //29 - ID: 184
            )
            16 -> listOf(
                BusStop(LatLng(46.995750819023456, -120.54472041778119), "Ruby & 4th", "Next Arrivals: ${getNextFiveTimes(RubyAnd4th16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184
                BusStop(LatLng(46.9958157369205, -120.54446364914462), "Ruby & 4th-2", "Next Arrivals: ${getNextFiveTimes(RubyAnd4thTwo16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184
                BusStop(LatLng(46.99888188503349, -120.54348527762896), "Sprague & 7th", "Next Arrivals: ${getNextFiveTimes(SpragueAnd7th16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184
                BusStop(LatLng(46.998891411318866, -120.54322965699548), "Sprague & 7th-2", "Next Arrivals: ${getNextFiveTimes(SpragueAnd7thTwo16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184
                BusStop(LatLng(47.00359846384709, -120.54345339624997), "Wildcat Way & 11th", "Next Arrivals: ${getNextFiveTimes(WildcatWayAnd11th16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184
                BusStop(LatLng(47.003543901899164, -120.54320019586363), "Wildcat Way & 11th-2", "Next Arrivals: ${getNextFiveTimes(WildcatWayAnd11thTwo16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184
                BusStop(LatLng(47.00599814971067, -120.54436170927636), "14th & Wildcat Way", "Next Arrivals: ${getNextFiveTimes(WildcatWayAnd14th16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184
                BusStop(LatLng(47.006178983378554, -120.54439462818168), "14th & Wildcat Way-2", "Next Arrivals: ${getNextFiveTimes(WildcatWayAnd14thTwo16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184
                BusStop(LatLng(47.006015202762875, -120.54711087633937), "14th & B St", "Next Arrivals: ${getNextFiveTimes(BStAndWildcatWay16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184
                BusStop(LatLng(47.00616115663477, -120.54747423555027), "14th & B St-2", "Next Arrivals: ${getNextFiveTimes(BStAndWildcatWayTwo16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184
                BusStop(LatLng(47.006440431007626, -120.55813276048383), "Coral & 15th", "Next Arrivals: ${getNextFiveTimes(CoralAnd15th16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184
                BusStop(LatLng(47.005802307498996, -120.55792370449947), "Coral & Rainer", "Next Arrivals: ${getNextFiveTimes(CoralAndRainer16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184
                BusStop(LatLng(47.00088143207408, -120.55787989789425), "Coral & University Way", "Next Arrivals: ${getNextFiveTimes(CoralAndUniversityWay16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184
                BusStop(LatLng(47.005609076947465, -120.56716322423833), "University Way Westbound", "Next Arrivals: ${getNextFiveTimes(UniversityWayWestBound16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184
                BusStop(LatLng(47.00399194198845, -120.56443733731903), "University Way (DSHS)", "Next Arrivals: ${getNextFiveTimes(UniversityWayDSHS16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184
                BusStop(LatLng(47.00644121431328, -120.56920856159846), "Critter Care on University", "Next Arrivals: ${getNextFiveTimes(CritterCareOnUniversity16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184
                BusStop(LatLng(47.00744466822952, -120.58633554818654), "I-90 West Interchange", "Next Arrivals: ${getNextFiveTimes(I90WestInterchange16).joinToString(", ")}", BitmapDescriptorFactory.HUE_BLUE, 16), //29 - ID: 184

            )
            // ... handle other routes
            else -> emptyList()
        }
    }
    }


