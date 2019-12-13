using Molby.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace Molby.Services
{
    public interface IUserService
    {
        Task<User> GetUserById(int id);
        Task<Level> GetUserCurrentLevel(int id);
    }
}
