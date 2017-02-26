# Sets the minimum version of CMake required to build the native
# library. You should either keep the default value or only pass a
# value of 3.4.0 or lower.

cmake_minimum_required(VERSION 3.4.1)

# Почему-то по умолчанию все ворнинги линковщика интерпретируются как ошибки,
# и не дают сделать билд.
# Флаги установлены в файле android-sdk\ndk-bundle\build\cmake\android.toolchain.cmake
# Строка 347
#	-Wl,--fatal-warnings)
# Как это отключить, неизвестно.
# Поэтому просто вырезаем эту строку из файла.
# Увы, но иначе никак.

# LOCAL_CFLAGS := -fshort-wchar -D_ANDROID -D_UNICODE -DNOT_RUBY -DEXPORT
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -fshort-wchar -D_ANDROID -D_UNICODE -DNOT_RUBY -DEXPORT")

set (qsp_SOURCES
	androidqspwrapper.c
qsp/actions.c
qsp/callbacks.c
qsp/codetools.c
qsp/coding.c
qsp/common.c
qsp/errors.c
qsp/game.c
qsp/locations.c
qsp/mathops.c
qsp/memwatch.c
qsp/menu.c
qsp/objects.c
qsp/playlist.c
qsp/statements.c
qsp/text.c
qsp/time.c
qsp/towlower.c
qsp/towupper.c
qsp/variables.c
qsp/variant.c
qsp/onig/regcomp.c
qsp/onig/regenc.c
qsp/onig/regerror.c
qsp/onig/regexec.c
qsp/onig/regparse.c
qsp/onig/regsyntax.c
qsp/onig/regtrav.c
qsp/onig/regversion.c
qsp/onig/st.c
qsp/onig/enc/ascii.c
qsp/onig/enc/cp1251.c
qsp/onig/enc/koi8_r.c
qsp/onig/enc/unicode.c
qsp/onig/enc/utf16_be.c
qsp/onig/enc/utf16_le.c
qsp/onig/enc/utf32_be.c
qsp/onig/enc/utf32_le.c
qsp/bindings/android/android_callbacks.c
qsp/bindings/android/android_coding.c
qsp/bindings/android/android_control.c
)


# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds it for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             # native-lib
             ndkqsp

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             # Associated headers in the same location as their source
             # file are automatically included.
             # src/main/cpp/native-lib.cpp

             ${qsp_SOURCES})

# Searches for a specified prebuilt library and stores the path as a
# variable. Because system libraries are included in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in the
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       # native-lib
                       ndkqsp

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
