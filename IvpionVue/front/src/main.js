import Vue from 'vue'
import Router from 'vue-router'
import App from './App.vue'
import Hello from './components/Hello.vue'
import Post from './components/Post.vue'
import NewPost from './components/NewPost.vue'
import UpdatePost from './components/UpdatePost.vue'
Vue.use(Router);

const router = new Router({
  routes:[
    {
      path: '/',
      name: 'home',
      component: Hello,
    },
    {
      path:'/post/:id',
      name:'post',
      component: Post,
      props: true,
    },
    {
      path:'/new',
      name: 'newPost',
      component: NewPost
    },
    {
      path:"/update",
      name:'update-post',
      component: UpdatePost,
      props: true,
    }
  ]
})

new Vue({
  el: '#app',
  render: h => h(App),
  router
})
