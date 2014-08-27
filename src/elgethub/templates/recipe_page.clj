(ns elgethub.templates.recipe-page
  (:require [net.cgrand.enlive-html :as html]))

(html/deftemplate recipe-page "recipe-page.html"
  [recipe-data]
  [:title#page-title] (html/content (str "Recipe Page for: "
                                         (:title recipe-data)))
  [:h2#recipe-title]  (html/content (:title recipe-data))
  [:p#recipe-desc]    (html/content (:desc recipe-data))
  [:pre#recipe-raw]   (html/content (:str recipe-data)))
