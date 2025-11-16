# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep Sora Editor
-keep class io.github.rosemoe.** { *; }

# Keep coroutine internals
-keep class kotlinx.coroutines.** { *; }

# Keep USB serial library
-keep class com.hoho.android.usbserial.** { *; }

# Don't strip native binaries
-keep class * extends java.lang.ProcessBuilder { *; }

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
