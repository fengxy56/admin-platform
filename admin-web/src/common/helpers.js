module.exports = {
    getQuery () {
        const url = window.location.search
        let querys = {}
        let strs
        if (url.indexOf('?') !== -1) {
            let str = url.substr(1)
            strs = str.split('&')
            for (let i = 0; i < strs.length; i++) {
                querys[strs[i].split('=')[0]] = decodeURIComponent(strs[i].split('=')[1])
            }
        }
        return querys
    },
    /**
     * 拼接查询字符串到URl
     * @param url
     * @param querys
     * @returns {string}
     */
    spliceQueryString (url, querys) {
        let strs = []
        let str
        for (let o in querys) {
            strs.push(o + '=' + encodeURIComponent(querys[o]))
        }
        str = url.indexOf('?') === -1 ? '?' : '&'
        return url + str + strs.join('&')
    },
    /**
     * 获取cookie中某个字段
     * @param name
     * @returns {null}
     */
    getCookie (name) {
        const reg = new RegExp(name + '=([^]+?);')
        const result = document.cookie.match(reg)
        return result ? result[1] : null
    },
    /**
     * 判断是否是个空对象
     * @param obj
     * @returns {boolean}
     */
    isEmptyObject (obj) {
        let prop
        for (prop in obj) {
            return false
        }
        return true
    },
    /**
     * 复制对象
     * @param to
     * @param _from
     * @returns {*}
     */
    extend (to, _from) {
        for (var key in _from) {
            to[key] = _from[key]
        }
        return to
    },
    /**
     * 延迟执行
     * @param func
     * @param wait
     * @returns {Function}
     */
    debounce (func, wait) {
        var timerId
        return function () {
            var self = this
            var args = arguments
            clearTimeout(timerId)
            timerId = setTimeout(function () {
                func.apply(self, Array.prototype.slice.call(args))
            }, wait)
        }
    },
    /**
     * IE polyfill:remove
     */
    removePolyfill (arr) {
        arr.forEach((item) => {
            item.remove = item.remove ||
                function () {
                    this.parentNode.removeChild(this)
                }
        })
    },

    /**
     * 产生[min, max]区间内，连续(step)的数组
     * @param {Number} min
     * @param {Number} max
     * @param {Number} step
     */
    range (min, max, step = 1) {
        const arr = []

        for (let index = min; index <= max; index += step) {
            arr.push(index)
        }

        return arr
    }
}
