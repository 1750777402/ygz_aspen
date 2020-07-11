import request from '@/utils/request'

// 获取所有的business
export function getBusinesses() {
  return request({
    url: 'api/businesses/',
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: 'api/businesses/',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/businesses/' + id + '/',
    method: 'delete'
  })
}

export function retrieve(id) {
  return request({
    url: 'api/businesses/' + id + '/',
    method: 'get'
  })
}

export function edit(id, data) {
  return request({
    url: 'api/businesses/' + id + '/',
    method: 'put',
    data
  })
}

export function save(id, data) {
  return request({
    url: 'api/businesses/' + id + '/',
    method: 'patch',
    data
  })
}
