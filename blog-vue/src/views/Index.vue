<template>
  <div v-title data-title="L星团的GreyWind">
		<div class="m-container">
			<div class="ui container">
				<div class="ui stackable grid">
					<div class="eleven wide column">
						<div class="ui segment">
							<article-scroll-page></article-scroll-page>
						</div>
						
					</div>
					<div div class="five wide column">
						<card-me class="me-area"></card-me>
						<card-tag :tags="hotTags"></card-tag>
						<card-article cardHeader="最热文章" :articles="hotArticles"></card-article>

						<card-archive cardHeader="文章归档" :archives="archives"></card-archive>

						<card-article cardHeader="最新文章" :articles="newArticles"></card-article>
					</div>

      

      
				 </div>
			</div>
		</div>
    </div>
</template>

<script>
  import CardMe from '@/components/card/CardMe'
  import CardArticle from '@/components/card/CardArticle'
  import CardArchive from '@/components/card/CardArchive'
  import CardTag from '@/components/card/CardTag'
  import ArticleScrollPage from '@/views/common/ArticleScrollPage'

  import {getArticles, getHotArtices, getNewArtices} from '@/api/article'
  import {getHotTags} from '@/api/tag'
  import {listArchives} from '@/api/article'

  export default {
    name: 'Index',
    created() {
      this.getHotArtices()
      this.getNewArtices()
      this.getHotTags()
      this.listArchives()
    },
    data() {
      return {
        hotTags: [],
        hotArticles: [],
        newArticles: [],
        archives: []
      }
    },
    methods: {
      getHotArtices() {
        let that = this
        getHotArtices().then(data => {
          that.hotArticles = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '最热文章加载失败!', showClose: true})
          }

        })

      },
      getNewArtices() {
        let that = this
        getNewArtices().then(data => {
          that.newArticles = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '最新文章加载失败!', showClose: true})
          }

        })

      },
      getHotTags() {
        let that = this
        getHotTags().then(data => {
          that.hotTags = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '最热标签加载失败!', showClose: true})
          }

        })
      },
      listArchives() {
		let that =this
        listArchives().then((data => {
          this.archives = data.data
        })).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '文章归档加载失败!', showClose: true})
          }
        })
      },

    },
    components: {
      'card-me': CardMe,
      'card-article': CardArticle,
      'card-tag': CardTag,
      ArticleScrollPage,
      CardArchive
    }
  }
</script>

<style scoped>

  .el-container {
    width: 960px;
	
	
  }

  .el-aside {
    margin-left: 20px;
    width: 260px;
  }

  .el-main {
    padding: 0px;
    line-height: 16px;
  }

  .el-card {
    border-radius: 0;
  }

  .el-card:not(:first-child) {
    margin-top: 20px;
  }
</style>
