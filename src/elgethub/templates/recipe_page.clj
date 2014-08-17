(ns elgethub.templates.recipe-page
  (:require [net.cgrand.enlive-html :as html]))

(html/deftemplate recipe "recipe-page.html"
  [ctxt]
  [:h2#recipe-title] (html/content (:recipe-title ctxt))
  [:#page-title]     (html/content (str "Recipe Page for: "
                                        (:recipe-title ctxt)))
  [:pre#recipe-raw]  (html/content (:recipe-str ctxt)))


