plugins {
    id("java")
    id("application")
}

group = "com.nicogtzl"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("redis.clients:jedis:5.1.0")

    // DigitalOcean used dependencies.
    implementation("org.apache.httpcomponents:httpclient:4.5.14")
    implementation("org.json:json:20231013")

    // Google Cloud Dependencies.
    implementation("com.google.cloud:google-cloud-compute:1.42.0")

    // Azure Cloud Dependencies.
    implementation(platform("com.azure:azure-sdk-bom:1.2.19"))
    implementation("com.azure:azure-storage-blob")
    implementation("com.azure.resourcemanager:azure-resourcemanager:2.33.0")




}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.nicogtzl.Main")
}
