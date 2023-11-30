object Dependencies {
    object Versions {
        const val Kotlin = "1.7.20"
        const val CoreKtx = "1.10.0"
        const val AppCompat = "1.6.1"
        const val Material = "1.8.0"
        const val ConstraintLayout = "2.1.4"
        const val RecyclerView = "1.3.2"

        const val Lifecycle = "2.6.1"
        const val ViewBindingDelegate = "1.5.8"
        const val Navigation = "2.5.3"
        const val Cicerone = "7.1"

        const val Room = "2.5.1"
        const val Koin = "3.4.0"
        const val Hawk = "2.0.1"

        const val Glide = "4.12.0"
    }

    object Plugins {
        const val Navigation =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.Navigation}"
    }

    object Core {
        const val CoreKtx = "androidx.core:core-ktx:${Versions.CoreKtx}"
        const val AppCompat = "androidx.appcompat:appcompat:${Versions.AppCompat}"
        const val Material = "com.google.android.material:material:${Versions.Material}"
        const val ConstraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.ConstraintLayout}"
        const val ViewBindingDelegate = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:${Versions.ViewBindingDelegate}"
        const val RecyclerView = "androidx.recyclerview:recyclerview:${Versions.RecyclerView}"
    }

    object Storage {
        const val RoomRuntime = "androidx.room:room-runtime:${Versions.Room}"
        const val RoomKtx = "androidx.room:room-ktx:${Versions.Room}"
        const val RoomKsp = "androidx.room:room-compiler:${Versions.Room}"
        const val Hawk = "com.orhanobut:hawk:${Versions.Hawk}"
    }

    object KTX {
        const val LifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Lifecycle}"
    }

    object Koin {
        const val Android = "io.insert-koin:koin-android:${Versions.Koin}"
        const val Navigation = "io.insert-koin:koin-androidx-navigation:${Versions.Koin}"
    }

    object Navigation {
        const val Runtime = "androidx.navigation:navigation-runtime-ktx:${Versions.Navigation}"
        const val Fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.Navigation}"
        const val Ui = "androidx.navigation:navigation-ui-ktx:${Versions.Navigation}"

        const val Cicerone = "com.github.terrakok:cicerone:${Versions.Cicerone}"
    }
}