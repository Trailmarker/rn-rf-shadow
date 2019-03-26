# React Native using shadow-cljs in 3 minutes

This is adapted from the PEZ [rn-rf-shadow](https://github.com/PEZ/rn-rf-shadow) example here by doing the 
minimum required to remove the Expo dependency.

The fastest way a [ClojureScript](https://clojurescript.org/) coder can get started with React Native. *Prove me wrong.*

This is an example project using: [shadow-cljs](https://github.com/thheller/shadow-cljs), [React Native](https://facebook.github.io/react-native/), [Reagent](https://reagent-project.github.io/), and [re-frame](https://github.com/Day8/re-frame).

<img src="./rn-rf-shadow.png" width="320" />

## Command line
```sh
$ npm install -g react-native-cli
$ yarn
$ shadow-cljs watch app
# wait for first compile to finish or expo gets confused
# start up an Android emulator or connect a device with USB debugging turned on
# in terminal #1
$ yarn start
# in terminal #2 
$ react-native run-android
```



## Production builds

To be updated.

## Some notes from Thomas Heller (retained from PEZ example)

(This project is built from this example of his: https://github.com/thheller/reagent-expo)

The `:app` build will create an `app/index.js`. In `release` mode that is the only file needed. In dev mode the `app` directory will contain many more `.js` files.

`:init-fn` is called after all files are loaded and in the case of `expo` must render something synchronously as it will otherwise complain about a missing root component. The `shadow.expo/render-root` takes care of registration and setup.

You should disable the `expo` live reload stuff and let `shadow-cljs` handle that instead as they will otherwise interfere with each other.

Source maps don't seem to work properly. `metro` propably doesn't read input source maps when converting sources as things are correctly mapped to the source .js files but not their sources.

Initial load in dev is quite slow since `metro` processes the generated `.js` files.

`reagent.core` loads `reagent.dom` which will load `react-dom` which we don't have or need. Including the `src/main/reagent/dom.cljs` to create an empty shell. Copied from [re-natal](https://github.com/drapanjanas/re-natal/blob/master/resources/cljs-reagent6/reagent_dom.cljs).
