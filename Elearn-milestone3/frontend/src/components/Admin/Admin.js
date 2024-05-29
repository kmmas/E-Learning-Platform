import { StudentTable, TeacherTable, Promote,Header,AdminNav } from '../index'
import './Admin.css'
const Admin = (props) => {
    return (
        <>
            {(props.user &&  props.user.page === 3) ?
                (
                    <>
                        <Header />
                        <Promote />
                        <StudentTable email={props.user.email} password={props.user.password} />
                        <TeacherTable email={props.user.email} password={props.user.password} />
                    </>
                ) :
                (props.user &&  props.user.page === 0) ?
                (
                    <>
                    <AdminNav />
                    <Promote />
                    <StudentTable email={props.user.email} password={props.user.password} />
                    <TeacherTable email={props.user.email} password={props.user.password} />
                </>  
                ):
                <h1 className='container admin-page'>You are not authorized to open this page. Please login to open this page</h1>
            }
        </>


    );
};
export default Admin;
