library(jsonlite)
library(networkD3)
library(webshot)

mySankey <- function(inFile) {
  inData <- jsonlite::fromJSON(inFile)
  
  df_flows <- inData$links
  df_nodes <- inData$nodes
  
  df_flows$source <- as.numeric(df_flows$source)
  df_flows$target <- as.numeric(df_flows$target)
  
  plot <- sankeyNetwork(df_flows, df_nodes, "source", "target","value", "names", units = inData$unit, NodeGroup = "group", fontSize = 14)
  
  return(plot)
}

hSankey <- function(inFile) {
  
  html <- "sankey.html"
  
  plot <- mySankey(inFile)
  
  saveNetwork(plot, html, TRUE)
  
  return(html)
}

iSankey <- function(inFile) {
  
  img <- "sankey.png"
  
  html <- hSankey(inFile)
  
  webshot::webshot(html, img)
  
  return(img)
}

