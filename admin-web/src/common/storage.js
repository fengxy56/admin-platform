/*
 * @Author: Liuxin
 * @Date: 2018-12-09 13:51:32
 * @Last Modified by: Liuxin
 * @Last Modified time: 2018-12-09 14:22:59
 * @Description 本地存储实现,封装localStorage和sessionStorage
 */

let store = {
  /* eslint-disable no-undef */
  storage: window.localStorage,
  session: {
    storage: window.sessionStorage
  }
}

const api = {
  set(key, val) {
    if (this.disabled) {
      return
    }
    if (val === undefined) {
      return this.remove(key)
    }
    this.storage.setItem(key, serialize(val))
    // console.log('【storage-|set】', key, '=>', serialize(val))
    return val
  },

  get(key, def) {
    if (this.disabled) {
      return def
    }
    let val = deserialize(this.storage.getItem(key))
    val = val === undefined ? def : val
    // console.log('【storage-|get】', key, '=>', val)
    return val
  },

  has(key) {
    // console.log('【storage-|has】', key, '=>', this.get(key) !== undefined)
    return this.get(key) !== undefined
  },

  remove(key) {
    if (this.disabled) {
      return
    }
    this.storage.removeItem(key)
    // console.log('【storage-|remove】', key)
  },

  clear() {
    if (this.disabled) {
      return
    }
    this.storage.clear()
    // console.log('【storage-|clear】')
  },

  getAll() {
    if (this.disabled) {
      return null
    }
    let ret = {}
    this.forEach((key, val) => {
      ret[key] = val
    })
    // console.log('【storage-|getAll】', ret)
    return ret
  },

  forEach(callback) {
    if (this.disabled) {
      return
    }
    for (let i = 0; i < this.storage.length; i++) {
      let key = this.storage.key(i)
      callback(key, this.get(key))
    }
  }
}

Object.assign(store, api)

Object.assign(store.session, api)

function serialize(val) {
  return JSON.stringify(val)
}

function deserialize(val) {
  if (typeof val !== 'string') {
    return undefined
  }
  try {
    return JSON.parse(val)
  } catch (e) {
    return val || undefined
  }
}

try {
  const testKey = 'store_js'
  store.set(testKey, testKey)
  if (store.get(testKey) !== testKey) {
    store.disabled = true
  }
  store.remove(testKey)
} catch (e) {
  store.disabled = true
}

export default store
