import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greeting()
    
	var body: some View {
		Text(greet)
	}
    
    init() {
        let fooVM = FooViewModel(store: FooStoreKt.store)
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
