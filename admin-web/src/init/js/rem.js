(function (global) {
  let window = global || this
  let width = 1920
  let docEl = window.document.documentElement
  let dpr = window.devicePixelRatio || 1
  let resizeEvt = 'resize'
  let resizeCall = (function f() {
    var clientWidth = docEl.clientWidth
    // var clientHeight = docEl.clientHeight
    // document.getElementsByClassName('.container').style.height = clientHeight + 'px'
    // console.log(document.getElementsByClassName('.container'))
    if (!clientWidth) {
      docEl.style.fontSize = 100 + 'px'
    } else {
      docEl.style.fontSize = 100 * (clientWidth / parseInt(width)) + 'px'
    }
    return f
  })()
  dpr = dpr >= 3 ? 3 : dpr >= 2 ? 2 : 1
  docEl.setAttribute('data-dpr', dpr)
  window.addEventListener && window.addEventListener(resizeEvt, resizeCall, false)
})(window)
