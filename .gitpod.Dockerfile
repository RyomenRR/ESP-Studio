FROM gitpod/workspace-full

# Java 17
RUN sudo apt update && sudo apt install -y openjdk-17-jdk

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV ANDROID_HOME=/workspace/android-sdk
ENV PATH=$PATH:$ANDROID_HOME/emulator:$ANDROID_HOME/platform-tools:$ANDROID_HOME/cmdline-tools/latest/bin

# Install Android SDK
RUN mkdir -p $ANDROID_HOME \
    && cd $ANDROID_HOME \
    && wget https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip \
    && unzip commandlinetools-linux-9477386_latest.zip -d cmdline-tools \
    && rm commandlinetools-linux-9477386_latest.zip \
    && mkdir -p cmdline-tools/latest \
    && mv cmdline-tools/cmdline-tools/* cmdline-tools/latest/
