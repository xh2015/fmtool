#network
##gson
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.**{ *; }
-keep class com.google.gson.examples.android.model.**{ *; }
-keep class com.google.gson.**{ *;}
##okhttp3
-dontwarn okio.**
-dontwarn okhttp3.**
-dontwarn com.squareup.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
###A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn javax.lang.model.element.Element
-keep class javax.lang.model.element.Element
