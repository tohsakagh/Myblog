import Vue from 'vue'
import VueRouter from 'vue-router'

const Welcome = () => import(/* webpackChunkName: "Welcome" */ '@/views/Welcome')
const Index = () => import(/* webpackChunkName: "Welcome" */ '@/components/Index')
const Blog = () => import(/* webpackChunkName: "Blog" */ '../components/Blog')
const BlogIndex = () => import(/* webpackChunkName: "Blog" */ '../components/BlogIndex')
const About = () => import(/* webpackChunkName: "Welcome" */ '../components/About')
const Essay = () => import(/* webpackChunkName: "Welcome" */ '../components/Essay')
const EssayInfo = () => import(/* webpackChunkName: "Welcome" */ '../components/EssayInfo')
const Message = () => import(/* webpackChunkName: "Welcome" */ '../components/Message')
const Project = () => import(/* webpackChunkName: "Welcome" */ '../components/Project')
const TimeStamp = () => import(/* webpackChunkName: "Welcome" */ '../components/TimeStamp')

const Home = () => import(/* webpackChunkName: "Home" */ '@/views/Home')
const AdminIndex = () => import(/* webpackChunkName: "Home" */ '@/components/admin/AdminIndex')
const Blogs = () => import(/* webpackChunkName: "Blog_Home" */ '../components/admin/Blogs')
const Blog_input = () => import(/* webpackChunkName: "Blog_Home" */ '../components/admin/Blog_input')
const Essays = () => import(/* webpackChunkName: "Essay" */ '../components/admin/Essays')
const Tags = () => import(/* webpackChunkName: "Tags" */ '../components/admin/Tags')
const Types = () => import(/* webpackChunkName: "Types" */ '../components/admin/Types')
const Administrator = () => import(/* webpackChunkName: "admin" */ '../components/admin/Administrator')
const Comments = () => import(/* webpackChunkName: "Comment" */ '../components/admin/Comments')
const Users = () => import(/* webpackChunkName: "User" */ '../components/admin/Users')
const Projects = () => import(/* webpackChunkName: "Projects" */ '../components/admin/Projects')

const Error = () => import(/* webpackChunkName: "Error" */ '@/components/Error')

Vue.use(VueRouter)

const routes = [
    {
        path: '/error',
        component: Error
    },
    {
        path: '/',
        component: Welcome,
        redirect: '/index',
        children: [
            {path: '/index', component: Index},
            {path: '/blogInfo', component: Blog},
            {path: '/blog', component: BlogIndex},
            {path: '/essay', component: Essay},
            {path: '/essayInfo', component: EssayInfo},
            {path: '/project', component: Project},
            {path: '/message', component: Message},
            {path: '/timeStamp', component: TimeStamp},
            {path: '/about', component: About}
        ]
    },
    {
        path: '/admin',
        component: Home,
        //挂载路由导航守卫
        beforeEnter: (to, from, next) => {
            // to 将要访问的路径
            // from 代表从哪个路径跳转而来
            // next 是一个函数，表示放行
            // next() 放行  next('login') 强制跳转
            const userInfo = JSON.parse(window.sessionStorage.getItem('user'))
            // console.log(userInfo)
            if (!userInfo) return next('/error')
            else {
                const type = userInfo.type
                // console.log(type)
                if (type !== '0') return next('/error')
                next()
            }
            next()
        },
        redirect: '/admin/index',
        children:[
            {path: '/admin/index', component: AdminIndex},
            {path: '/admin/blogs', component: Blogs},
            {path: '/admin/blog-input', component: Blog_input},
            {path: '/admin/essays', component: Essays},
            {path: '/admin/types', component: Types},
            {path: '/admin/tags', component: Tags},
            {path: '/admin/Administrator', component: Administrator},
            {path: '/admin/projects', component: Projects},
            {path: '/admin/comments', component: Comments},
            {path: '/admin/users', component: Users},
        ]
    }
]

const router = new VueRouter({
    routes
})


export default router