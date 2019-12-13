using Molby.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace Molby.Services
{
    public interface IGroupService
    {
        Task<List<Group>> GetGroupsList();
        Task<Group> GetGroupById(int id);
    }
}
