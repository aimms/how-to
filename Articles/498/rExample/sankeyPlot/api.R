#* @get /
healthCheck <- function() {
  msg = paste0("Hello world, at '", as.character(Sys.time()), "'")
  return(msg)
}


source("sankey.R")

#* Generate a sankey PNG
#* @get /sankey
#* @serializer contentType list(type='image/png')
sankey = function(req) {
  inFile <- req$postBody
  file <- iSankey(inFile)
  readBin(file,'raw',n = file.info(file)$size)
}

#* Generate a sankey HTML
#* @get /sankeyhtml
#* @serializer contentType list(type='image/png')
sankeyhtml = function(req, res) {
  inFile <- req$postBody
  file <- hSankey(inFile)
  include_html(file, res)
  
}

