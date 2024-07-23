# SSH Client App for Android

This project is an SSH client application developed using Kotlin for Android. It enables users to manage and connect to SSH servers directly from their Android devices, executing commands securely.

## Acknowledgments

This project is based on the original SSH Client project. The original project is no longer being maintained, but it served as a foundation for further development and enhancements in this version. You can find the original repository at [https://github.com/antonio-paulino/sshclient](https://github.com/antonio-paulino/sshclient).

## Features

- **Manage SSH Connections**: Users can add, edit, and delete SSH server configurations.
- **Execute Commands**: Send commands to SSH servers.
- **Secure Connection**: Utilizes encrypted connections to communicate with SSH servers, ensuring data security.
- **User-friendly Interface**: Offers a clean and intuitive interface for managing SSH connections.
- **Navigation Drawer**: Provides easy navigation through the app with a slide-out drawer.
- **Floating Action Button**: Allows quick access to add a new SSH connection.
- **Form Validation**: Ensures that all user input is valid before creating a new SSH client.
- **Scrollable Form**: Prevents overflow when the keyboard is opened, enhancing the user experience.

## Technologies Used

- **Programming Languages**: Kotlin, Java
- **Framework**: Android SDK
- **IDE**: Android Studio
- **Build System**: Gradle
- **UI Toolkit**: Jetpack Compose - for building the modern, declarative UI of the app.
- **Libraries**:
    - **SSHJ**: For handling SSH connections, authentication, and command execution.
    - **Room**: For local data storage, managing SSH client configurations persistently.

## Prerequisites

- Android Studio Koala | 2024.1.1 or higher
- JDK 8 or higher
- Android SDK with API Level 23 (Android 6.0) or higher

## Setup and Installation

1. Clone the repository to your local machine.
   ```sh
   git clone https://github.com/antonio-paulino/sshclient-android.git
   ```
2. Open the project in Android Studio.
3. Build and run the project on an Android emulator or physical device.

## Contributing

Contributions are welcome! Feel free to open an issue or submit a pull request for any changes or improvements you would like to make.

## License

Distributed under the MIT License. See [LICENSE](LICENSE) for more information.