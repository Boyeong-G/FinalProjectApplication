### *** 참고 사항(build.gradle) ***
</br>
plugins {</br>
&nbsp;&nbsp;&nbsp;&nbsp;alias(libs.plugins.androidApplication)</br>
&nbsp;&nbsp;&nbsp;&nbsp;alias(libs.plugins.jetbrainsKotlinAndroid)</br>
&nbsp;&nbsp;&nbsp;&nbsp;id("kotlin-kapt")</br>
&nbsp;&nbsp;&nbsp;&nbsp;id("com.google.gms.google-services")</br>
}</br>

android {</br>
&nbsp;&nbsp;&nbsp;&nbsp;viewBinding {</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;enable = true</br>
&nbsp;&nbsp;&nbsp;&nbsp;}</br>
}</br>

dependencies {</br>

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("androidx.preference:preference:1.2.1")

    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    implementation("com.tickaroo.tikxml:annotation:0.8.13")
    implementation("com.tickaroo.tikxml:core:0.8.13")
    implementation("com.tickaroo.tikxml:retrofit-converter:0.8.13")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt("com.tickaroo.tikxml:processor:0.8.13")

    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("com.google.android.gms:play-services-auth:21.1.1")
    implementation("com.google.firebase:firebase-firestore-ktx:25.0.0")
    implementation("com.google.firebase:firebase-storage-ktx:21.0.0")
}</br>
