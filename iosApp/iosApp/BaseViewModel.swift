//
//  BaseViewModel.swift
//  iosApp
//
//  Created by GEUNHO CHOI on 2022/09/17.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
import RxSwift
import RxRelay

protocol StoreAware {
    associatedtype S : shared.State
    associatedtype A : shared.Action
    associatedtype E : shared.Event
    associatedtype T : Store<S,A,E>
    
    var holder : StoreWrapper<S,A,E> { get }
    
    init(store:T)
}

class BaseViewModel<STATE : shared.State, ACTION : shared.Action, EVENT : shared.Event> : StoreAware {
    typealias S = STATE
    typealias A = ACTION
    typealias E = EVENT
    typealias T = Store<S,A,E>
    
    let holder: StoreWrapper<S,A,E>
    let state: ReplaySubject<S>
    let event: PublishSubject<E>
    let disposedBag = DisposeBag()
    
    required init(store: T) {
        self.holder = StoreWrapper(store: store)
        self.state = ReplaySubject<S>.create(bufferSize: 1)
        self.event = PublishSubject<E>()
        holder.watchState { state in
            self.state.onNext(state)
        }
        holder.watchEvent { event in
            self.event.onNext(event)
        }
    }
    
    deinit {
        holder.close()
    }
}

class FooViewModel: BaseViewModel<FooState, FooAction, FooEvent> {
    let age = PublishRelay<Int32>()
    let name = PublishRelay<String>()
    
    required init(store: T) {
        super.init(store:store)
        
        
        state.subscribe(
            onNext: { state in
            }
        )
        
        event.subscribe(
            onNext: { state in
            }
        )
        
        state.map { $0.age }
            .bind(to: self.age)
            .disposed(by: disposedBag)
        
        state.map { $0.name }
            .bind(to: self.name)
            .disposed(by: disposedBag)
        
    }
}
