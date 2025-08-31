# Firebase rules
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# Tika metadata extraction (para o teu GetMetadata.java)
-keep class org.apache.tika.** { *; }
-keep class org.xml.sax.** { *; }

# GSON (para o SongsToJSON.java)
-keep class com.google.gson.** { *; }
-keepattributes Signature