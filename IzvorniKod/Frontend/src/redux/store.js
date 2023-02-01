import {createStore} from "redux";
import rootReducers from "./reducers";
import { persistStore, persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage";

const persistConfiguration = {
    key: 'root',
    storage
}

const persistedReducer = persistReducer(persistConfiguration, rootReducers);
let store = createStore(persistedReducer, window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__())
let persistor = persistStore(store)

export { store, persistor }