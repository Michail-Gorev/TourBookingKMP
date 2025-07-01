import SwiftUI
import shared

extension KoinHelper {
    func doInitKoin() {
        KoinHelper.shared.doInitKoinFromSwift()
    }
}

@main
struct iOSApp: App {
    init() {
        initKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
