#!/usr/bin/env kotlinc -script

import java.io.File

fun clear(target: File) {
    target.delete()
    File("ffmpeg2pass-0.log").delete()
    File("ffmpeg2pass-0.log.mbtree.temp").delete()
}

fun ffmpeg(args: String) {
    Runtime.getRuntime()
        .exec("ffmpeg $args")
        .errorStream.copyTo(System.out)
}

if (args.isEmpty()) {
	println("ffmpeg.kts <file name> <from seconds> <duration seconds>")
} else {
	val src = File(args[0])
	val target = File("$src-result.mp4")
	val start = args[1].toInt()
	val time = args[2].toInt()

	clear(target)
	//ffmpeg("-y -ss $start -t $end -i $src -strict -2 -pass 1 -b:v 500k -f webm -threads 0 /dev/null")
	//ffmpeg("-ss $start -t $end -i $src -strict -2 -pass 2 -b:v 500k -threads 0 $target")
	ffmpeg("-y -ss $start -t $time -i $src -strict -2 -pass 1 -b:v 200k -f mp4 -movflags faststart -threads 0 /dev/null")
	ffmpeg("-ss $start -t $time -i $src -strict -2 -pass 2 -b:v 200k -f mp4 -movflags faststart -threads 0 $target")
}