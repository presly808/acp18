<template lang="html">
  <div>
  <div class="post" v-if="post">
    <h1 class="post__title">{{ post.title }}</h1>
    <p class="post__body">{{ post.body }}</p>
    <p class="post__id">{{ post.id }}</p>
  </div>
  <div class="btn-group" v-if="post">
   <!-- <button class="butt" @click="showUpdate = true" >Update</button>
    <update-post v-model="post" class="upd-post" v-if="showUpdate" @close="showUpdate = false"></update-post>
    --><button class="butt" @click="deletePost">Delete</button>
  </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import UpdatePost from "./UpdatePost";

  export default{
    components: {UpdatePost},
    props: ['id'],

    data(){
      return{
        showUpdate: false,
        post:null,
        endpoint: 'http://localhost:8080/post/{id}'
      }
    },
    methods:{
      getPost(id){
        axios(this.endpoint.replace("{id}", id)).then(response=>{
          this.post = response.data
        })
          .catch(errors=>{
            console.log(errors)
          })
      },
      deletePost: function () {
        fetch("http://localhost:8080/post/delete/{id}".replace("{id}", this.post.id), {
          method: "DELETE"
        })
          .then(() => this.$router.push("/"))
          .catch(errors => {
            console.log(errors)
          })
      }
    },
    created(){
      this.getPost(this.id);
    },
    watch:{
      '$route'(){
        this.getPost(this.id)
      }
    }
  }
</script>
<style lang="scss" scoped>
  .post {
    position: relative;
    max-width: 500px;
    margin: 0 auto;
    padding: 50px 20px 70px;
    &__title {
      position: relative;
      text-transform: uppercase;
      z-index: 1;
    }
    &__body {
      position: relative;
      z-index: 1;
    }
    &__id {
      position: absolute;
      font-size: 200px;
      bottom: -50px;
      margin: 0;
      color: #eeeeee;
      right: -20px;
      line-height: 1;
      font-weight: 900;
      z-index: 0;
    }

  }
  .btn-group .butt:hover {
    background-color: #3e8e41;
  }
  .btn-group .butt {
    background-color: #4CAF50; /* Green */
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    cursor: pointer;
    float: left;
  }
  .btn-group{
    position: absolute;
    right: 20px;
    bottom: 20px;
  }
</style>
