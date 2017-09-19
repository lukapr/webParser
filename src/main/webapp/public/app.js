// var Config = React.createClass({
//     getInitialState: function () {
//         return {display: true};
//     },
//
//     handleDelete() {
//         var self = this;
//         $.ajax({
//             url: self.props.config._links.self.href,
//             type: 'DELETE',
//             success: function(result) {
//                 self.setState({display: false});
//             },
//             error: function(xhr, ajaxOptions, thrownError) {
//                 toastr.error(xhr.responseJSON.message);
//             }
//         });
//     },
//
//     render: function () {
//         if (this.state.display===false) return null;
//         else return (
//             <tr>
//                 <td>{this.props.config.name}</td>
//                 <td>{this.props.config.description}</td>
//                 <td>{this.props.config.link}</td>
//                 <td>
//                     <button className="btn btn-info" onClick={this.handleDelete}>Delete</button>
//                 </td>
//             </tr>);
//     }
// });
// var ConfigTable = React.createClass({
//     render: function () {
//         var rows = [];
//         this.props.configs.forEach(function (config) {
//             rows.push(
//                 <Config config={config} key={config.name} />);
//         });
//         return (
//             <div className="container">
//                 <table className="table table-striped">
//                     <thead>
//                     <tr>
//                         <th>Name</th>
//                         <th>description</th>
//                         <th>link</th>
//                     </tr>
//                     </thead>
//                     <tbody>{rows}</tbody>
//                 </table>
//             </div>);
//     }
// });
// var App = React.createClass({
//
//     loadConfigsFromServer: function () {
//         var self = this;
//         $.ajax({
//             url: "http://localhost:8080/api/configs"
//         }).then(function (data) {
//             self.setState({configs: data._embedded.configs});
//         });
//     },
//
//     getInitialState: function () {
//         return {configs: []};
//     },
//
//     componentDidMount: function () {
//         this.loadConfigsFromServer();
//     },
//
//     render() {
//         return ( <ConfigTable configs={this.state.configs}/> );
//     }
// });
// ReactDOM.render(<App/>, document.getElementById('root'));