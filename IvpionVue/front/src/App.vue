<template>
  <div id="app">
    <!--login , register , show all register users-->
    <header>
      <h1>Vue.js SPA</h1>
    </header>
    <main>
      <aside class="sidebar">
        <router-link
          v-for="post in posts"
          active-class="is-active"
          class="link"
          :to="{ name: 'post', params: { id: post.id } }">
          {{post.id}}. {{post.title}}

        </router-link>
        <div class="new-post">
          <button class="button" @click="showModal = true">Add new post</button>
          <newPost v-if="showModal" @updatePosts="refreshPostsList()"  @close="showModal = false"> <!-- <- user component name(newPost) as tag for links-->
            <h3 slot="header">Create your post</h3>
          </newPost>
        </div>
      </aside>
      <div class="content">
        <router-view></router-view>
      </div>
    </main>
  </div>
</template>

<script>
  import axios from 'axios'
  import NewPost from "./components/NewPost"; //<- import component

  export default {
    name: 'app',
    components: {NewPost},// <- реєстрація дочірніх компононтів для поточного компоненту
    data() {
      return {
        posts: [],
        showModal: false,
        endpoint: 'http://localhost:8080/post/all'
      }
    },
    created() {
      this.getAllPosts();
    },
    methods: {
      refreshPostsList() {

        this.getAllPosts();
      },
      getAllPosts() {
        //todo move to fetch
        axios.get(this.endpoint).then(response => {
          this.posts = response.data;
        }).catch(error => {
          console.log('----error----');
          console.log(error);
        })
      },
    },
    watch:{
      '$route':"refreshPostsList"
    }
  }
</script>

<style lang="scss">
  body {
    margin: 0;
    padding: 0;
  }

  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
  }

  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  header {
    position: fixed;
    top: 0;
    width: 100%;
    min-height: 90px;
    border-bottom: 1px solid #42b983;
    text-align: center;
    background: #ffffff;
  }

  main {
    display: flex;
    height: calc(100vh - 90px);
    max-width: 1200px;
    margin-top: 90px;
    margin-left: auto;
    margin-right: auto;
    overflow: hidden;
  }

  aside {
    flex: 1 0 20%;
    height: 100%;
    overflow-y: auto;
    width: 30%;
    padding: 50px 30px;
    box-sizing: border-box;
    border-right: 1px solid #42b983;
  }

  .content {
    flex: 1 1 80%;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .link {
    display: block;
    text-decoration: none;
    margin-bottom: 10px;
    color: #2c3e50;
    &--home {
      text-transform: uppercase;
      margin-bottom: 30px;
    }
    &.is-active {
      color: #42b983;
    }
  }

  .button {
    background-color: #4CAF50;
    border: none;
    color: white;
    padding: 12px 26px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
  }

  .new-post {
    position: absolute;
    left:  20px;
    top: 20px;
  }
</style>
