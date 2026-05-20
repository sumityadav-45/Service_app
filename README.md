# Asynchronous Background Service & IPC Counter Engine 🚀

A high-performance native Android application engineered in **Kotlin** that showcases decoupled multi-threading execution via Android's native `Service` component. 

The application deploys an isolated background thread to compute system cycles continuously and streams asynchronous telemetry packets back to the foreground layout interface using local Inter-Process Communication (IPC) broadcast events.

---

## 🛠️ System Architecture & Concurrency Model

*   **🧵 Non-Blocking Worker Thread:** Spawns a dedicated task thread within the custom lifecycle of `MyBackgroundService`, guaranteeing that heavy computational interval loops never obstruct the Main UI thread.
*   **📡 Reactive Inter-Process Communication:** Utilizes implicit action broadcast pipelines (`sendBroadcast`) to send system states safely across decoupled application contexts.
*   **🛡️ Android 13+ Security Compliance:** Gracefully handles architectural security enforcement introduced in API level 33 (Tiramisu) by programmatically introducing explicit context registration flags (`Context.RECEIVER_EXPORTED`).
*   **🔄 START_STICKY Resiliency Flag:** Overrides the service lifecycle return statement with `START_STICKY`, instructing the Android OS kernel to automatically reconstruct the background worker process if it gets killed due to low memory constraints.
*   **🎨 Cyberpunk HUD Display Layout:** Features an immersive dark dashboard (`#0F172A`) hosting specialized neon counter segments (`#00E676`) wrapped inside highly optimized Material Design action widgets.

---

## 📐 Data-Flow & Telemetry Pipeline

```mermaid
graph TD
    UI[User Clicks START SERVICE] -->|Explicit Intent| SVC[MyBackgroundService Launched]
    SVC -->|onStartCommand: START_STICKY| Thread[Spawn Isolated Background Thread]
    
    subgraph Background Execution Layer
        Thread --> Loop{Is Running True?}
        Loop -- Yes --> Sleep[Thread.sleep 1000ms]
        Sleep --> Increment[Count Variable +1]
        Increment --> Broadcast[Package Intent: COUNTER_UPDATED]
    end
    
    Broadcast -->|Implicit IPC Stream| Rec[Dynamic BroadcastReceiver]
    
    subgraph Foreground Viewport Layer
        Rec -->|onReceive Intercept| Extract[Extract Extra Float Packets]
        Extract --> Render[Update tvCounter Widget Text]
    end
    
    UserStop[User Clicks STOP SERVICE] -->|stopService| Dest[onDestroy Triggered]
    Dest -->|isRunning = false| Loop -- No / Halt --> Kill[Terminate Background Thread safely]
