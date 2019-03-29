(ns example.reloader
  (:require
   ["react-native" :as rn]
   [reagent.core :as r]))

(defonce root-ref (atom nil))
(defonce root-component-ref (atom nil))

(defn render-root
  "Render the application root element in a Reagent application.
  Adapted from Shadow-CLJS the shadow.expo namespace."
  [root]
  (let [first-call? (nil? @root-ref)]
    (reset! root-ref root)

    (if-not first-call?
      (when-let [root @root-component-ref]
        (.forceUpdate ^js root))
      (let [Root (fn []
                   (r/create-class
                    {:componentDidMount
                     (fn []
                       (this-as this
                         (reset! root-component-ref this)))
                     :componentWillUnmount
                     (fn []
                       (reset! root-component-ref nil))
                     :reagent-render
                     (fn []
                       (let [body @root-ref]
                         (if (fn? body)
                           (body)
                           body)))}))]

        (.registerComponent rn/AppRegistry "rn-rf-shadow" #(r/reactify-component Root))))))

