(ns elgethub.templates.recipe-page
  (:require [net.cgrand.enlive-html :as html]))

(html/deftemplate recipe "recipe-page.html"
  [ctxt]
  [:title#page-title]     (html/content (str "Recipe Page for: "
                                             (:recipe-title ctxt)))
  [:h2#recipe-title] (html/content (:recipe-title ctxt))
  [:p#recipe-desc]   (html/content (:recipe-desc ctxt))
  [:pre#recipe-raw]  (html/content (:recipe-str ctxt)))
